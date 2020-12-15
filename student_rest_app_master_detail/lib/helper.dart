import 'student.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';

class Helper {
  Future<List<Student>> fetchStudents() async {
    final response =
        await http.get('https://api4all.azurewebsites.net/api/People');
    if (response.statusCode == 200) {
      return parseStudents(response.body);
    } else {
      throw Exception('Unable to fetch students from the REST API');
    }
  }

  List<Student> parseStudents(String responseBody) {
    final parsed = json.decode(responseBody).cast<Map<String, dynamic>>();
    return parsed.map<Student>((json) => Student.fromJson(json)).toList();
  }
}
