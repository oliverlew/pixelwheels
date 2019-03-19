/*
 * Copyright 2018 Aurélien Gâteau <mail@agateau.com>
 *
 * This file is part of Pixel Wheels.
 *
 * Pixel Wheels is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.agateau.pixelwheels.stats;

import com.agateau.utils.CollectionUtils;

import java.util.HashMap;

public class GameStats {
    private final transient IO mIO;
    final HashMap<String, TrackStats> mTrackStats = new HashMap<String, TrackStats>();
    final HashMap<String, Integer> mBestChampionshipRank = new HashMap<String, Integer>();
    final HashMap<String, Integer> mEvents = new HashMap<String, Integer>();

    public enum Event {
        MISSILE_HIT
    }

    public interface IO {
        void setGameStats(GameStats gameStats);
        void load();
        void save();
    }

    public GameStats(IO io) {
        mIO = io;
        mIO.setGameStats(this);
        mIO.load();
    }

    public TrackStats getTrackStats(String trackId) {
        TrackStats stats = mTrackStats.get(trackId);
        if (stats == null) {
            stats = addTrack(trackId);
        }
        return stats;
    }

    TrackStats addTrack(String trackId) {
        TrackStats stats = new TrackStats(mIO);
        mTrackStats.put(trackId, stats);
        return stats;
    }

    public int getBestChampionshipRank(String id) {
        //noinspection ConstantConditions
        return CollectionUtils.getOrDefault(mBestChampionshipRank, id, Integer.MAX_VALUE);
    }

    public void onChampionshipFinished(String id, int rank) {
        Integer currentBest = mBestChampionshipRank.get(id);
        if (currentBest == null || currentBest > rank) {
            mBestChampionshipRank.put(id, rank);
            mIO.save();
        }
    }

    public void recordEvent(Event event) {
        String id = event.toString();
        Integer count = mEvents.get(id);
        if (count == null) {
            count = 0;
        }
        ++count;
        mEvents.put(id, count);
        mIO.save();
    }

    public int getEventCount(Event event) {
        //noinspection ConstantConditions
        return CollectionUtils.getOrDefault(mEvents, event.toString(), 0);
    }
}
