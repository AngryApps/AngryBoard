import 'dart:convert';

class CardEntity {
  final String id;
  final String title;
  final String description;
  final DateTime createdAt;
  final DateTime updatedAt;
  final String columnId;
  final int position;

  CardEntity(
    this.id,
    this.title,
    this.description,
    this.createdAt,
    this.updatedAt,
    this.columnId,
    this.position,
  );

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'title': title,
      'description': description,
      'createdAt': createdAt.millisecondsSinceEpoch,
      'updatedAt': updatedAt.millisecondsSinceEpoch,
      'columnId': columnId,
      'position': position,
    };
  }

  factory CardEntity.fromMap(Map<String, dynamic> map) {
    return CardEntity(
      map['id'] ?? '',
      map['title'] ?? '',
      map['description'] ?? '',
      DateTime.parse(map['createdAt']),
      DateTime.parse(map['updatedAt']),
      map['columnId'] ?? '',
      map['position']?.toInt() ?? 0,
    );
  }

  String toJson() => json.encode(toMap());

  factory CardEntity.fromJson(String source) =>
      CardEntity.fromMap(json.decode(source));
}
