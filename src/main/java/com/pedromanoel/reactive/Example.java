package com.pedromanoel.reactive;

public abstract class Example {
    private String name;

    public Example(String name) {
        this.name = name;
    }

    public void run() {
        printStart();
        example();
        printFinish();
    }

    protected abstract void example();

    private void printStart() {
        System.out.println(String.format("# Example - %s\n", name));
    }

    private void printFinish() {
        System.out.println(String.format("## Finished example - %s\n", name));
    }
}
