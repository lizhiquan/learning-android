class Student {
  final int id;
  final String lastName;
  final String firstName;
  final String occupation;
  final String gender;
  final String pictureUrl;
  final int votes;

  Student(this.id, this.lastName, this.firstName, this.occupation, this.gender, this.pictureUrl, this.votes);

  factory Student.fromJson(Map<String, dynamic> json) {
    return Student(
      json['id'],
      json['lastName'],
      json['firstName'],
      json['occupation'],
      json['gender'],
      json['pictureUrl'],
      json['votes'],
    );
  }
}
