package com.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class WallLambda extends Wall {

    protected class WallLambdaIterator extends WallIterator {
        public WallLambdaIterator(List<Block> blocks) {
            super(blocks);
        }

        public void forEachBlock(Predicate<Block> fun) {
            while (hasNextBlock()) {
                Block block = nextBlock().get();
                if (fun.test(block))
                    return;
            }
        }
    }

    public WallLambda() {
        super();
    }

    public WallLambda(List<Block> blocks) {
        super(blocks);
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        final Stack<Block> blockWrapper = new Stack<>();
        WallLambdaIterator wallLambdaIterator = new WallLambdaIterator(this.blocks);
        wallLambdaIterator.forEachBlock((block) -> {
            if (block.getColor() == color) {
                blockWrapper.add(block);
                return true;
            }
            return false;
        });
        if (blockWrapper.empty())
            return Optional.empty();
        return Optional.of(blockWrapper.peek());
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        List<Block> foundBlocks = new ArrayList<>();
        WallLambdaIterator wallLambdaIterator = new WallLambdaIterator(this.blocks);
        wallLambdaIterator.forEachBlock((block) -> {
            if (block.getMaterial() == material) {
                foundBlocks.add(block);
            }
            return false;
        });
        return foundBlocks;
    }

    @Override
    public int count() {
        final AtomicInteger count = new AtomicInteger(0);
        WallLambdaIterator wallLambdaIterator = new WallLambdaIterator(this.blocks);
        wallLambdaIterator.forEachBlock((block) -> {
            count.incrementAndGet();
            return false;
        });
        return count.get();
    }
}
