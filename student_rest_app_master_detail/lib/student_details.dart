import 'package:flutter/material.dart';

import 'student.dart';

class StudentDetails extends StatelessWidget {
  StudentDetails({Key key, this.item}) : super(key: key);
  final Student item;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(this.item.id.toString()),
      ),
      body: Center(
        child: Container(
          padding: EdgeInsets.all(0),
          child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Expanded(
                    child: Container(
                        padding: EdgeInsets.all(5),
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: <Widget>[
                            Text("Student ID: ${this.item.id.toString()}",
                                style: TextStyle(fontWeight: FontWeight.bold)),
                            Text("First Name: ${this.item.firstName}"),
                            Text("Last Name: ${this.item.lastName}"),
                            Text("School: ${this.item.occupation}"),
                          ],
                        )))
              ]),
        ),
      ),
    );
  }
}
