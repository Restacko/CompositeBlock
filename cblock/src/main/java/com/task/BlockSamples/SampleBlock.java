package com.task.BlockSamples;

import com.task.Block;

public class SampleBlock implements Block {
    String color;
    String material;

    public SampleBlock(String color, String material) {
        this.color = color;
        this.material = material;
    }
    public String getColor() {
        return color;
    }
    public String getMaterial() {
        return material;
    }
}
