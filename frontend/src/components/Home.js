import React from "react";
import styles from "../constants/styles";

export default function Home({
  setMode,
  showOperatorLogin,
  setShowOperatorLogin,
  operatorUsername,
  setOperatorUsername,
  operatorPassword,
  setOperatorPassword,
  operatorError,
  handleOperatorLogin,
  onTestUserClick,
}) {
  return (
    <div style={styles.container}>
      <button style={styles.bigButton} onClick={onTestUserClick}>
        Test User
      </button>
      <button style={styles.bigButton} onClick={() => setShowOperatorLogin(true)}>
        Test Operator
      </button>
      {showOperatorLogin && (
        <div style={styles.operatorLoginOverlay}>
          <div style={styles.operatorLoginBox}>
            <h3>Operator Login</h3>
            <input
              style={styles.operatorInput}
              type="text"
              placeholder="Username"
              value={operatorUsername}
              onChange={(e) => setOperatorUsername(e.target.value)}
            />
            <input
              style={styles.operatorInput}
              type="password"
              placeholder="Password"
              value={operatorPassword}
              onChange={(e) => setOperatorPassword(e.target.value)}
            />
            {operatorError && <div style={{ color: "red", marginBottom: 10 }}>{operatorError}</div>}
            <button style={styles.operatorButton} onClick={handleOperatorLogin}>
              Login
            </button>
            <button
              style={{ ...styles.operatorButton, backgroundColor: "#dc3545", marginTop: 10 }}
              onClick={() => setShowOperatorLogin(false)}
            >
              Cancel
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
