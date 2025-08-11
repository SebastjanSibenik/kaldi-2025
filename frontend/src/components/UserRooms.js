import React from "react";
import styles from "../constants/styles";

export default function UserRooms({ rooms, setSelectedRoom, goHome }) {
  return (
    <div style={styles.container}>
      <h2 style={styles.sectionTitle}>Select a Room</h2>
      <ul style={styles.roomList}>
        {rooms.map((room) => (
          <li
            key={room.roomType}
            onClick={() => setSelectedRoom(room)}
            style={styles.roomItem}
          >
            {room.name}
          </li>
        ))}
      </ul>
      <button onClick={goHome} style={styles.homeButton}>Go Home</button>
    </div>
  );
}
