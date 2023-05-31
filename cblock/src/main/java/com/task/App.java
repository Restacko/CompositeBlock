package com.task;

import java.util.ArrayList;
import java.util.List;

import com.task.Block;
import com.task.Wall;
import com.task.BlockSamples.GluedBlock;
import com.task.BlockSamples.SampleBlock;


public class App {
    public static void main(String[] args) {
    
        List<Block> blocks3 = new ArrayList<>();
        blocks3.add(new SampleBlock("White3", "Concrete"));
        blocks3.add(new SampleBlock("Red3", "Sand"));
        blocks3.add(new GluedBlock(null));
        blocks3.add(new SampleBlock("Orange3", "Dust"));
        blocks3.add(new SampleBlock("Gray3", "mat"));
        
        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new SampleBlock("White2", "Concrete"));
        blocks2.add(new SampleBlock("Red2", "Sand"));
        blocks2.add(new GluedBlock(blocks3));
        blocks2.add(new SampleBlock("Orange2", "Dust"));
        blocks2.add(new SampleBlock("Gray2", "mat"));
        
        List<Block> blocks = new ArrayList<>();
        blocks.add(new SampleBlock("White", "Concrete"));
        blocks.add(new SampleBlock("Red", "Sand"));
        blocks.add(new GluedBlock(blocks2));
        blocks.add(new SampleBlock("Orange", "Dust"));
        blocks.add(new SampleBlock("Gray", "mat"));
        blocks.add(new GluedBlock(null));

        Wall wall = new Wall(blocks);
        WallLambda wallLambda = new WallLambda(blocks);
        // wall.printColors();
        List<Block> sandBlocks = wall.findBlocksByMaterial("Sand");
        for (Block block : sandBlocks) {
            System.out.println(block.getColor());
        }
        System.out.println(wall.findBlockByColor("Red"));
        System.out.println(wall.count());

        System.out.println("\nWall Lambda\n");

        sandBlocks = wallLambda.findBlocksByMaterial("Sand");
        for (Block block : sandBlocks) {
            System.out.println(block.getColor());
        }
        System.out.println(wallLambda.findBlockByColor("Red"));
        System.out.println(wallLambda.count());
    }
}
