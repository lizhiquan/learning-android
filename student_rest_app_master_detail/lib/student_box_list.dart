import 'package:flutter/material.dart';

import 'student.dart';
import 'student_box.dart';
import 'student_details.dart';

class StudentBoxList extends StatelessWidget {
  final List<Student> items;
  StudentBoxList({Key key, this.items});

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: items.length,
      itemBuilder: (context, index) {
        return GestureDetector(
          child: StudentBox(item: items[index]),
          onTap: () {
            Navigator.push(
              context,
              MaterialPageRoute(
                builder: (context) => StudentDetails(item: items[index]),
              ),
            );
          },
        );
      },
    );
  }
}
