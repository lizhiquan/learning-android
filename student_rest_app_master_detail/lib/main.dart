import 'package:flutter/material.dart';
import 'dart:async';
import 'student.dart';
import 'helper.dart';
import 'student_box_list.dart';

void main() => runApp(
      MaterialApp(
        theme: ThemeData.dark(),
        home: Home(
          title: 'Student Navigation demo home page',
          students: new Helper().fetchStudents(),
        ),
      ),
    );

class Home extends StatelessWidget {
  final String title;
  final Future<List<Student>> students;
  Home({Key key, this.title, this.students}) : super(key: key);

  // final items = Student.getStudents();
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: Text("Toons by Vincent")),
        body: Center(
          child: FutureBuilder<List<Student>>(
            future: students,
            builder: (context, snapshot) {
              if (snapshot.hasError) print(snapshot.error);

              return snapshot.hasData
                  ? StudentBoxList(items: snapshot.data)

                  // return the ListView widget :

                  : Center(child: CircularProgressIndicator());
            },
          ),
        ));
  }
}
