import React from "react";
import styles from "../constants/styles";

export default function UserRooms({ rooms, setSelectedRoom, fetchRoomDetails, goHome }) {
  const handleRoomClick = (room) => {
    fetchRoomDetails(room.type);
    setSelectedRoom(room);
  };

  return (
    <div style={styles.container}>
      <h2>Select a Room</h2>
      <button onClick={goHome} style={styles.backButton}>Back</button>
      <ul style={styles.roomList}>
        {rooms.map((room) => (
          <li
            key={room.type}
            style={styles.roomItem}
            onClick={() => handleRoomClick(room)}
          >
            {room.name}
          </li>
        ))}
      </ul>
    </div>
  );
}
