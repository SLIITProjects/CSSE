package com.example.csse.csse.Model;

public class Journey {


  String userId;
  String Name;
  String Start;


  public Journey() {
  }

  public Journey(String userId, String name, String start) {
    this.userId = userId;
    Name = name;
    Start = start;
  }

  public String getUserId() {
    return userId;
  }

  public String getName() {
    return Name;
  }

  public String getStart() {
    return Start;
  }
}
