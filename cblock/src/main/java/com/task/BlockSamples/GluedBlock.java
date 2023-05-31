package com.task.BlockSamples;

import java.util.ArrayList;
import java.util.List;

import com.task.Block;
import com.task.CompositeBlock;

public class GluedBlock implements CompositeBlock {
    String color;
    String material;
    List<Block> blocks;

    public GluedBlock(List<Block> blocks) {
        this("mix","mix", blocks);
    }

    public GluedBlock(String color, String material, List<Block> blocks) {
        this.color = color;
        this.material = material;
        if(blocks != null)
            this.blocks = blocks;
        else
            this.blocks = new ArrayList<>();
    }
    @Override
    public String getColor(){
        return color;
    }
    @Override
    public String getMaterial(){
        return material;
    }

    @Override
    public List<Block> getBlocks() {
        return blocks;
    }

}
