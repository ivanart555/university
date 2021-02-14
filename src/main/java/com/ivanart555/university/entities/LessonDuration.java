package com.ivanart555.university.entities;

import java.time.LocalDateTime;

public class LessonDuration {
    private LocalDateTime lssonStart;
    private LocalDateTime lssonEnd;

    public LocalDateTime getLssonStart() {
        return lssonStart;
    }

    public void setLssonStart(LocalDateTime lssonStart) {
        this.lssonStart = lssonStart;
    }

    public LocalDateTime getLssonEnd() {
        return lssonEnd;
    }

    public void setLssonEnd(LocalDateTime lssonEnd) {
        this.lssonEnd = lssonEnd;
    }

    @Override
    public String toString() {
        return "LessonDuration [lssonStart=" + lssonStart + ", lssonEnd=" + lssonEnd + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lssonEnd == null) ? 0 : lssonEnd.hashCode());
        result = prime * result + ((lssonStart == null) ? 0 : lssonStart.hashCode());
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
        LessonDuration other = (LessonDuration) obj;
        if (lssonEnd == null) {
            if (other.lssonEnd != null)
                return false;
        } else if (!lssonEnd.equals(other.lssonEnd))
            return false;
        if (lssonStart == null) {
            if (other.lssonStart != null)
                return false;
        } else if (!lssonStart.equals(other.lssonStart))
            return false;
        return true;
    }
}
