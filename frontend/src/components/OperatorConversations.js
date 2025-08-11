import React from "react";
import styles from "../constants/styles";

export default function OperatorConversations({ allConversations, claimConversation, setSelectedRoom, setConversationUuids, fetchMessages, goHome }) {
  return (
    <div style={styles.container}>
      <h2 style={styles.sectionTitle}>Conversations</h2>
      <table style={styles.table}>
        <thead>
          <tr>
            <th style={styles.th}>UUID</th>
            <th style={styles.th}>Room</th>
            <th style={styles.th}>Status</th>
            <th style={styles.th}>Claim/View</th>
          </tr>
        </thead>
        <tbody>
          {allConversations.map((conv) => {
            const roomType = conv.roomType || conv.roomDto?.roomType || "Unknown";
            const roomName = conv.roomDto?.name || roomType;
            const status = conv.status || "PENDING";
            const uuid = conv.uuid;

            const handleViewConversation = () => {
              setSelectedRoom({ type: roomType, name: roomName });
              setConversationUuids((prev) => ({
                ...prev,
                [roomType]: uuid,
              }));
              fetchMessages(uuid, roomType);
            };

            return (
              <tr key={uuid}>
                <td style={styles.td}>{uuid}</td>
                <td style={styles.td}>{roomName}</td>
                <td style={styles.td}>{status}</td>
                <td style={styles.td}>
                  {status === "PENDING" ? (
                    <button style={styles.claimButton} onClick={() => claimConversation(uuid)}>
                      Claim
                    </button>
                  ) : (
                    <button
                      style={{ ...styles.claimButton, backgroundColor: "#6c757d", cursor: "pointer" }}
                      onClick={handleViewConversation}
                    >
                      View
                    </button>
                  )}
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>

      <button style={styles.homeButton} onClick={goHome}>
        Back to Home
      </button>
    </div>
  );
}
