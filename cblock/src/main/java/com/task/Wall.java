package com.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class Wall implements Structure {
    private static final boolean COUNT_COMPOSITES = false;
    private static final boolean LIST_COMPOSITES = false;
    private static final boolean SKIP_COMPOSITES = true;
    private List<Block> blocks;
    private Stack<Iterator<Block>> stack;

    public Wall() {
        blocks = new ArrayList<>();
        stack = new Stack<>();
    }

    public Wall(List<Block> blocks) {
        this();
        if (blocks != null)
            this.blocks = blocks;
    }

    private void setIterator() {
        stack = new Stack<>();
        stack.add(blocks.iterator());
    }

    private boolean hasNextBlock() {
        while (!stack.empty() && !stack.peek().hasNext())
            stack.pop();
        if (stack.empty())
            return false;
        return stack.peek().hasNext();
    }

    private Optional<Block> nextBlock() {
        Block block = stack.peek().next();
        if (block instanceof CompositeBlock) {
            CompositeBlock cBlock = (CompositeBlock) block;
            if (!cBlock.getBlocks().isEmpty())
                stack.add(cBlock.getBlocks().iterator());
        }
        return Optional.of(block);
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        setIterator();
        while (hasNextBlock()) {
            Block block = nextBlock().get();
            if (block instanceof CompositeBlock && SKIP_COMPOSITES)
                continue;
            if (block.getColor() == color)
                return Optional.of(block);
        }
        return Optional.empty();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        List<Block> blocks = new ArrayList<>();
        setIterator();
        while (hasNextBlock()) {
            Block block = nextBlock().get();
            if (block instanceof CompositeBlock && !LIST_COMPOSITES)
                continue;
            if (block.getMaterial().equals(material))
                blocks.add(block);
        }
        return blocks;
    }

    @Override
    public int count() {
        int count = 0;
        setIterator();
        while (hasNextBlock()) {
            if (nextBlock().get() instanceof CompositeBlock && !COUNT_COMPOSITES)
                continue;
            count++;
        }
        return count;
    }

    public void printColors() {
        setIterator();
        while (hasNextBlock()) {
            System.out.println(nextBlock().get().getColor());
        }
    }

}
