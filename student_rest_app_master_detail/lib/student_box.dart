import 'package:flutter/material.dart';
import 'student.dart';

class StudentBox extends StatelessWidget {
  StudentBox({Key key, this.item}) : super(key: key);
  final Student item;

  Widget build(BuildContext context) {
    return Container(
        padding: EdgeInsets.all(2),
        height: 140,
        child: Card(
          child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: <Widget>[
                Container(
                  padding: EdgeInsets.all(5),
                  child: Text(this.item.id.toString(),
                      style: TextStyle(
                          fontWeight: FontWeight.bold, color: Colors.amber)),
                ),
                Container(
                  padding: EdgeInsets.all(5),
                  child: Image.network(this.item.pictureUrl),
                  width: 50,
                ),
                Expanded(
                  child: Container(
                    padding: EdgeInsets.all(5),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: <Widget>[
                        Text(
                          "${this.item.firstName} ${this.item.lastName}",
                          style: TextStyle(color: Colors.deepOrange),
                        ),
                        Text(this.item.occupation),
                      ],
                    ),
                  ),
                ),
              ]),
        ));
  }
}
