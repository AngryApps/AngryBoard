import 'package:angryapp/domain/entities/entities.dart';
import 'package:flutter/material.dart';

class ModalBottomSheetTargetColumn {
  // TODO injetar aqui um serviço de Strings - Para pegar título e afins

  static show(BuildContext context, List<ColumnEntity> columns) =>
      showModalBottomSheet<void>(
        context: context,
        builder: (BuildContext context) {
          return Container(
            height: MediaQuery.of(context).size.height,
            width: MediaQuery.of(context).size.width,
            decoration: BoxDecoration(
              color: Color.fromRGBO(215, 204, 200, 1),
              borderRadius: BorderRadius.only(
                topLeft: Radius.circular(20),
                topRight: Radius.circular(20),
              ),
            ),
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: <Widget>[
                  SizedBox(
                    height: MediaQuery.of(context).size.height * 0.08,
                    child: Center(
                      child: Text(
                        "Select Target Column",
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                  ),
                  Expanded(
                    child: ListView.builder(
                      itemCount: columns.length,
                      itemBuilder: ((context, index) {
                        final column = columns[index];
                        return Padding(
                          padding: const EdgeInsets.symmetric(horizontal: 4.0),
                          child: Card(
                            elevation: 4,
                            child: ListTile(
                              title: Text(
                                column.title,
                                textAlign: TextAlign.start,
                                style: TextStyle(
                                  color: Color.fromRGBO(113, 95, 89, 1),
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                              subtitle: Text(
                                column.description,
                                style: TextStyle(
                                  color: Color.fromRGBO(113, 95, 89, 1),
                                ),
                              ),
                              onTap: () {},
                            ),
                          ),
                        );
                      }),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: ElevatedButton(
                      onPressed: () {},
                      style: ButtonStyle(
                        backgroundColor: WidgetStateProperty.all<Color>(
                          Color.fromRGBO(121, 86, 72, 1),
                        ),
                      ),
                      child: Text(
                        "Save",
                        style: TextStyle(color: Colors.white),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          );
        },
      );
}
