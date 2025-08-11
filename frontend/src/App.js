import React, { useState, useEffect, useCallback } from "react";

import styles from "./constants/styles";
import { API_BASE } from "./api/api";
import ConversationView from './components/ConversationView';

import Home from "./components/Home";
import UserRooms from "./components/UserRooms";
import OperatorConversations from "./components/OperatorConversations";

function App() {
  const [mode, setMode] = useState(null);
  const [rooms, setRooms] = useState([]);
  const [selectedRoom, setSelectedRoom] = useState(null);
  const [messagesByRoom, setMessagesByRoom] = useState({});
  const [conversationUuids, setConversationUuids] = useState({});
  const [inputMessage, setInputMessage] = useState("");
  const [showOperatorLogin, setShowOperatorLogin] = useState(false);
  const [operatorUsername, setOperatorUsername] = useState("");
  const [operatorPassword, setOperatorPassword] = useState("");
  const [operatorError, setOperatorError] = useState("");
  const [operatorAuthHeader, setOperatorAuthHeader] = useState(null);
  const [allConversations, setAllConversations] = useState([]);
  const [userConversationUuid, setUserConversationUuid] = useState(null);
  const [username, setUsername] = useState("");

  useEffect(() => {
    setInputMessage("");
  }, [selectedRoom]);

  const encodeBasicAuth = (username, password) =>
    "Basic " + btoa(`${username}:${password}`);

  // Generic fetch helper
  const fetchJSON = async (url, options = {}) => {
    const res = await fetch(url, options);
    if (!res.ok) throw new Error(`Error ${res.status}: ${res.statusText}`);
    return res.json();
  };

  // Fetch conversations (for operator)
  const fetchConversations = useCallback(() => {
    if (!operatorAuthHeader) return;

    fetchJSON(`${API_BASE}/conversations`, {
      headers: { Authorization: operatorAuthHeader },
    })
      .then(setAllConversations)
      .catch(console.error);
  }, [operatorAuthHeader]);

  // Fetch messages for a conversation (operator)
  const fetchMessages = useCallback(
    (uuid, roomType) => {
      if (!operatorAuthHeader) return;

      fetchJSON(`${API_BASE}/conversations/${uuid}/messages`, {
        headers: { Authorization: operatorAuthHeader },
      })
        .then((msgs) => {
          setMessagesByRoom((prev) => ({
            ...prev,
            [roomType]: msgs,
          }));
        })
        .catch(console.error);
    },
    [operatorAuthHeader]
  );

  // Claim a conversation (operator)
  const claimConversation = (uuid) => {
    if (!operatorAuthHeader) return;

    fetchJSON(`${API_BASE}/conversations/${uuid}/claim`, {
      method: "POST",
      headers: { Authorization: operatorAuthHeader },
    })
      .then((data) => {
        const conversation = data.conversation || data;
        const roomType =
          conversation.roomDto?.roomType ||
          conversation.roomDto?.name ||
          conversation.roomType ||
          "Unknown";
        const roomName = conversation.roomDto?.name || roomType || "Unknown";
        const convUuid = conversation.uuid || uuid;

        setConversationUuids((prev) => ({ ...prev, [roomType]: convUuid }));
        setSelectedRoom({ type: roomType, name: roomName });

        fetchMessages(convUuid, roomType);
        fetchConversations();
      })
      .catch(console.error);
  };

// Operator sends message
const sendMessage = async () => {
  if (!inputMessage.trim() || !selectedRoom) return;

  const uuid = conversationUuids[selectedRoom.type];
  if (!uuid) return;

  try {
    await fetchJSON(`${API_BASE}/conversations/${uuid}/reply`, {
      method: "POST",
      headers: {
        Authorization: operatorAuthHeader,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        content: inputMessage,
        userDto: {
          username: operatorUsername || "operator",
          role: "OPERATOR",
        },
      }),
    });

    setInputMessage("");
    fetchMessages(uuid, selectedRoom.type);
  } catch (error) {
    console.error(error);
  }
};

  // Operator login handler
  const handleOperatorLogin = () => {
    if (!operatorUsername || !operatorPassword) {
      setOperatorError("Please enter username and password.");
      return;
    }

    const authHeader = encodeBasicAuth(operatorUsername, operatorPassword);
    setOperatorAuthHeader(authHeader);

    fetch(`${API_BASE}/conversations`, {
      headers: { Authorization: authHeader },
    })
      .then((res) => {
        if (!res.ok) throw new Error("Invalid credentials");
        return res.json();
      })
      .then((data) => {
        setAllConversations(data);
        setShowOperatorLogin(false);
        setOperatorError("");
        setMode("operator");
      })
      .catch(() => {
        setOperatorError("Login failed. Check your credentials.");
        setOperatorAuthHeader(null);
      });
  };

  // Fetch all rooms for "Test User"
  const fetchRoomsForTestUser = () => {
    fetchJSON(`${API_BASE}/rooms`)
      .then((data) => {
        setRooms(data);
        setMode("user");
      })
      .catch((err) => alert("Error fetching rooms: " + err.message));
  };

  // Room select handler (user)
  const handleRoomSelect = (room) => {
    setSelectedRoom(room);
    setUserConversationUuid(null);
    setUsername("");
    fetchRoomDetails(room.roomType || room.type);
  };

  // Fetch messages for a room by roomType (user mode)
  const fetchRoomDetails = (roomType) => {
    fetchJSON(`${API_BASE}/rooms/${roomType}`)
      .then((data) => {
        setMessagesByRoom((prev) => ({
          ...prev,
          [roomType]: data.messages || [],
        }));
      })
      .catch((err) => alert("Error fetching room details: " + err.message));
  };

// User sends message
const sendUserMessage = async () => {
  if (!inputMessage.trim() || !selectedRoom || !username.trim()) {
    alert("Please enter a username and message.");
    return;
  }

  const roomType = selectedRoom.roomType || selectedRoom.type;

  try {
    if (!userConversationUuid) {
      // Start new conversation
      const startRes = await fetchJSON(`${API_BASE}/conversations/start`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          content: inputMessage,
          roomType: roomType,
          userDto: {
            username: username,
            role: "USER",
          },
        }),
      });

      const newUuid = startRes.conversation.uuid;
      setUserConversationUuid(newUuid);

      setMessagesByRoom((prev) => {
        const roomMsgs = prev[roomType] || [];
        return {
          ...prev,
          [roomType]: [
            ...roomMsgs,
            {
              content: inputMessage,
              userDto: { role: "USER", username },
              roomDto: { name: selectedRoom.name },
            },
          ],
        };
      });

      setInputMessage("");
    } else {
      // Reply to existing conversation
      await fetchJSON(`${API_BASE}/conversations/${userConversationUuid}/reply`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          content: inputMessage,
          userDto: {
            username: username,
            role: "USER",
          },
        }),
      });

      setMessagesByRoom((prev) => {
        const roomMsgs = prev[roomType] || [];
        return {
          ...prev,
          [roomType]: [
            ...roomMsgs,
            {
              content: inputMessage,
              userDto: { role: "USER", username },
              roomDto: { name: selectedRoom.name },
            },
          ],
        };
      });

      setInputMessage("");
    }
  } catch (error) {
    alert(error.message);
  }
};

  // Reset app state to initial
  const goHome = () => {
    setMode(null);
    setSelectedRoom(null);
    setMessagesByRoom({});
    setConversationUuids({});
    setInputMessage("");
    setOperatorAuthHeader(null);
    setOperatorUsername("");
    setOperatorPassword("");
    setOperatorError("");
    setShowOperatorLogin(false);
    setRooms([]);
    setAllConversations([]);
    setUserConversationUuid(null);
    setUsername("");
  };

  return (
    <>
      {!mode && (
        <Home
          setMode={setMode}
          showOperatorLogin={showOperatorLogin}
          setShowOperatorLogin={setShowOperatorLogin}
          operatorUsername={operatorUsername}
          setOperatorUsername={setOperatorUsername}
          operatorPassword={operatorPassword}
          setOperatorPassword={setOperatorPassword}
          operatorError={operatorError}
          handleOperatorLogin={handleOperatorLogin}
          onTestUserClick={fetchRoomsForTestUser}
        />
      )}

      {mode === "user" && !selectedRoom && (
        <UserRooms rooms={rooms} setSelectedRoom={handleRoomSelect} goHome={goHome} />
      )}

      {mode === "user" && selectedRoom && (
        <>
          {/* Username input for user mode */}
          <div style={{ marginBottom: 12 }}>
            <input
              type="text"
              placeholder="Enter your username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              style={{ padding: 8, width: 250 }}
            />
          </div>

          <ConversationView
            selectedRoom={selectedRoom}
            messages={messagesByRoom[selectedRoom.roomType || selectedRoom.type] || []}
            inputMessage={inputMessage}
            setInputMessage={setInputMessage}
            sendMessage={sendUserMessage}
            goBack={() => {
              setSelectedRoom(null);
              setUserConversationUuid(null);
              setUsername("");
            }}
          />
        </>
      )}

      {mode === "operator" && (
        <>
          {!selectedRoom && (
            <OperatorConversations
              allConversations={allConversations}
              claimConversation={claimConversation}
              setSelectedRoom={setSelectedRoom}
              setConversationUuids={setConversationUuids}
              fetchMessages={fetchMessages}
              goHome={goHome}
            />
          )}
          {selectedRoom && (
            <ConversationView
              selectedRoom={selectedRoom}
              messages={messagesByRoom[selectedRoom.type] || []}
              inputMessage={inputMessage}
              setInputMessage={setInputMessage}
              sendMessage={sendMessage}
              goBack={() => setSelectedRoom(null)}
            />
          )}
        </>
      )}
    </>
  );
}

export default App;