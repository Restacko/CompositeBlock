package com.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class WallLambda implements Structure {
    private static final boolean COUNT_COMPOSITES = false;
    private static final boolean LIST_COMPOSITES = false;
    private static final boolean SKIP_COMPOSITES = true;
    private List<Block> blocks;
    private Stack<Iterator<Block>> stack;

    public WallLambda() {
        blocks = new ArrayList<>();
        stack = new Stack<>();
    }

    public WallLambda(List<Block> blocks) {
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

    private void forEachBlock(Predicate <Block> fun){
        setIterator();
        while (hasNextBlock()) {
            Block block = nextBlock().get();
            if (block instanceof CompositeBlock && SKIP_COMPOSITES)
                continue;
            if (fun.test(block))
                return;
        }

    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        final Stack<Block> wrapperBlock = new Stack<>();
        forEachBlock((block) -> {
           if(block.getColor() == color){
                wrapperBlock.add(block);
                return true;
           }
           return false;
        });
        if(stack.empty())
            return Optional.empty();
        return Optional.of(wrapperBlock.peek());
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        List<Block> blocks = new ArrayList<>();
        forEachBlock((block) -> {
            if(block.getMaterial() == material){
                 blocks.add(block);
            }
            return false;
         });
        return blocks;
    }

    @Override
    public int count() {
        final AtomicInteger count = new AtomicInteger(0);
        forEachBlock((block) -> {
            count.incrementAndGet();
            return false;
         });
         return count.get();
    }

    public void printColors() {
        setIterator();
        while (hasNextBlock()) {
            System.out.println(nextBlock().get().getColor());
        }
    }

}
