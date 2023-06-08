package com.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class Wall implements Structure, Iterable<Block> {
    protected List<Block> blocks;

    protected class WallIterator implements Iterator<Block>{
        protected Stack<Block> blocksStack;

        public WallIterator(List<Block> blocks) {
            this.blocksStack = new Stack<>();
            this.blocksStack.addAll(blocks);
        }
        @Override
        public boolean hasNext() {
            return !this.blocksStack.empty();
        }
        @Override
        public Block next() {
            Block block = blocksStack.pop();
            if (block instanceof CompositeBlock) {
                CompositeBlock compositeBlock = (CompositeBlock) block;
                if (!compositeBlock.getBlocks().isEmpty())
                    this.blocksStack.addAll(compositeBlock.getBlocks());
            }
            return block;
        }
    }

    public Wall() {
        this.blocks = new ArrayList<>();
    }

    public Wall(List<Block> blocks) {
        this();
        if (blocks != null)
            this.blocks = blocks;
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        for (Block block : this) {
            if (block.getColor() == color)
                return Optional.of(block);
        }
        return Optional.empty();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        List<Block> foundBlocks = new ArrayList<>();
        for (Block block : this) {
            if (block.getMaterial().equals(material))
                foundBlocks.add(block);
        }
        return foundBlocks;
    }

    @Override
    public int count() {
        int count = 0;
        for(Block block : this){
            count++;
        }   
        return count;
    }

    @Override
    public Iterator<Block> iterator() {
        return new WallIterator(blocks);
    }
}
