package com.task;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.task.BlockSamples.GluedBlock;
import com.task.BlockSamples.SampleBlock;


public class AppTest 
{
    List<Block> blocks4;
    List<Block> blocks3;
    List<Block> blocks2;
    List<Block> blocks;
    @Before
    public void SetUp(){
        blocks4 = new ArrayList<>();
        blocks4.add(new SampleBlock("color5", "mat2"));
        blocks4.add(new SampleBlock("color2", "mat4"));
        blocks4.add(new SampleBlock("color1", "mat1"));

        blocks3 = new ArrayList<>();
        blocks3.add(new SampleBlock("White3", "Concrete"));
        blocks3.add(new SampleBlock("Red3", "mat"));
        blocks3.add(new GluedBlock(null));
        blocks3.add(new SampleBlock("Orange3", "Dust"));
        blocks3.add(new SampleBlock("Gray3", "mat"));
        
        blocks2 = new ArrayList<>();
        blocks2.add(new SampleBlock("White2", "Concrete"));
        blocks2.add(new SampleBlock("Red2", "Sand"));
        blocks2.add(new GluedBlock(blocks3));
        blocks2.add(new SampleBlock("Orange2", "Dust"));
        blocks2.add(new SampleBlock("Gray", "mat"));
        
        blocks = new ArrayList<>();
        blocks.add(new SampleBlock("White", "Concrete"));
        blocks.add(new SampleBlock("Red", "Sand"));
        blocks.add(new GluedBlock(blocks2));
        blocks.add(new SampleBlock("Orange", "Dust"));
        blocks.add(new SampleBlock("Gray", "mat"));
        blocks.add(new GluedBlock(null));
    }

    @Test
    public void emptyWallCount()
    {
        Wall wall = new Wall();
        assertTrue(wall.count() == 0);
        wall = new Wall(new ArrayList<>());
        assertTrue(wall.count() == 0);
    }
    @Test
    public void simpleWallCount(){
        Wall wall = new Wall(blocks4);
        assertTrue(wall.count() == 3);
    }
    @Test
    public void wallWithCompositesBlocksCount(){
        Wall wall = new Wall(blocks);
        assertTrue(wall.count() == 12);
    }

    @Test
    public void emptyWallGetColor(){
        Wall wall = new Wall();
        assertTrue(wall.findBlockByColor("Red").equals(Optional.empty()));
    }

    @Test
    public void noSuchColor(){
        Wall wall = new Wall(blocks);
        assertTrue(wall.findBlockByColor("Black").equals(Optional.empty()));
    }

    @Test
    public void colorMatch(){
        Wall wall = new Wall(blocks);
        assertTrue(wall.findBlockByColor("Gray").get().getColor() == "Gray");
    }

    @Test
    public void emptyWallGetMaterials() {
        Wall wall = new Wall();
        assertTrue(wall.findBlocksByMaterial("mat").equals(new ArrayList<>()));
        wall = new Wall(new ArrayList<>());
        assertTrue(wall.findBlocksByMaterial("mat").equals(new ArrayList<>()));
    }

    @Test
    public void noSuchMaterial() {
        Wall wall = new Wall(blocks);
        assertTrue(wall.findBlocksByMaterial("nomaterial").equals(new ArrayList<>()));
    }

    @Test
    public void materialCountWithComposites() {
        Wall wall = new Wall(blocks);
        assertTrue(wall.findBlocksByMaterial("mat").size() == 4);
    }

    @Test
    public void materialCountSimple() {
        Wall wall = new Wall(blocks4);
        assertTrue(wall.findBlocksByMaterial("mat4").size() == 1);
    }




    

}
