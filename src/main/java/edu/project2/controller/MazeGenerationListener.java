package edu.project2.controller;

import java.util.EventListener;

public interface MazeGenerationListener extends EventListener {
    void respondMazeGenerationRequest();
}
