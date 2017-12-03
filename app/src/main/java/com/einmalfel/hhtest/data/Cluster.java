package com.einmalfel.hhtest.data;

public class Cluster {
  public final String id;
  public final String name;
  public final Item[] items;

  public Cluster(String id, String name, Item[] items) {
    this.id = id;
    this.name = name;
    this.items = items;
  }

  public class Item {
    public final String name;
    public final String url;
    public final int count;

    public Item(String name, String url, int count)    {
      this.name = name;
      this.url = url;
      this.count = count;
    }
  }
}
