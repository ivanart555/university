package com.ivanart555.university.entities;

public class Classroom {
    private int roomId;
    private String roomName;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return "Classroom [roomId=" + roomId + ", roomName=" + roomName + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + roomId;
        result = prime * result + ((roomName == null) ? 0 : roomName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Classroom other = (Classroom) obj;
        if (roomId != other.roomId)
            return false;
        if (roomName == null) {
            if (other.roomName != null)
                return false;
        } else if (!roomName.equals(other.roomName))
            return false;
        return true;
    }
}
