package com.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class Wall implements Structure {
    protected static final boolean COUNT_COMPOSITES = false;
    protected static final boolean LIST_COMPOSITES = false;
    protected static final boolean SKIP_COMPOSITES = true;
    protected List<Block> blocks;

    protected class WallIterator {
        protected Stack<Iterator<Block>> iteratorStack;

        public WallIterator(List<Block> blocks) {
            this.iteratorStack = new Stack<>();
            this.iteratorStack.add(blocks.iterator());
        }

        public boolean hasNextBlock() {
            while (!this.iteratorStack.empty() && !iteratorStack.peek().hasNext())
                this.iteratorStack.pop();
            if (this.iteratorStack.empty())
                return false;
            return this.iteratorStack.peek().hasNext();
        }

        public Optional<Block> nextBlock() {
            Block block = iteratorStack.peek().next();
            if (block instanceof CompositeBlock) {
                CompositeBlock compositeBlock = (CompositeBlock) block;
                if (!compositeBlock.getBlocks().isEmpty())
                    this.iteratorStack.add(compositeBlock.getBlocks().iterator());
            }
            return Optional.of(block);
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
        WallIterator wallIterator = new WallIterator(this.blocks);
        while (wallIterator.hasNextBlock()) {
            Block block = wallIterator.nextBlock().get();
            if (block instanceof CompositeBlock && SKIP_COMPOSITES)
                continue;
            if (block.getColor() == color)
                return Optional.of(block);
        }
        return Optional.empty();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        List<Block> foundBlocks = new ArrayList<>();
        WallIterator wallIterator = new WallIterator(this.blocks);
        while (wallIterator.hasNextBlock()) {
            Block block = wallIterator.nextBlock().get();
            if (block instanceof CompositeBlock && !LIST_COMPOSITES)
                continue;
            if (block.getMaterial().equals(material))
                foundBlocks.add(block);
        }
        return foundBlocks;
    }

    @Override
    public int count() {
        int count = 0;
        WallIterator wallIterator = new WallIterator(this.blocks);
        while (wallIterator.hasNextBlock()) {
            if (wallIterator.nextBlock().get() instanceof CompositeBlock && !COUNT_COMPOSITES)
                continue;
            count++;
        }
        return count;
    }
}
