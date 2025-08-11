import React from "react";
import styles from "../constants/styles";

function ConversationView({ selectedRoom, messages, inputMessage, setInputMessage, sendMessage, goBack }) {
  return (
    <div>
      <h2>Room: {selectedRoom.name}</h2>
      <div style={styles.conversationBox}>
        {messages.length === 0 ? (
          <p>No messages yet.</p>
        ) : (
          <table style={styles.table}>
            <thead>
              <tr>
                <th style={styles.th}>User</th>
                <th style={styles.th}>Message</th>
              </tr>
            </thead>
            <tbody>
              {messages.map((msg, i) => (
                <tr key={i}>
                  <td style={styles.td}>
                    {msg.userDto?.username ||
                     msg.username ||
                     msg.conversation?.userDto?.role ||
                     msg.userRole ||
                     "unknown"}
                  </td>
                  <td style={styles.td}>{msg.content}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
      <div style={styles.inputArea}>
        <input
          value={inputMessage}
          onChange={(e) => setInputMessage(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && sendMessage()}
          placeholder="Type your message"
          style={styles.input}
        />
        <button onClick={sendMessage} style={styles.sendButton}>Send</button>
        <button onClick={goBack} style={styles.homeButton}>Back</button>
      </div>
    </div>
  );
}

export default ConversationView;
