import 'package:angryapp/domain/entities/entities.dart';
import 'package:angryapp/ui/mixins/loading_manager.dart';
import 'package:angryapp/ui/pages/home/home_presenter.dart';
import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  final HomePresenter presenter;

  const HomePage({super.key, required this.presenter});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> with LoadingManager {
  ColumnEntity message = ColumnEntity(
    "",
    "",
    "",
    DateTime.now(),
    DateTime.now(),
    0,
  );

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text("AngryApp"),
        centerTitle: true,
      ),
      body: Builder(builder: (_) {
        handleLoading(context, widget.presenter.isLoadingStream);

        widget.presenter.messageStream.listen((event) {
          setState(() {
            message = event;
            widget.presenter.listColumns();
          });
        });
        return SingleChildScrollView(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Card(
                elevation: 5,
                child: ListTile(
                  title: Text("${message.title} - ${message.id}"),
                  subtitle: Text(message.description),
                ),
              ),
              StreamBuilder(
                stream: widget.presenter.listColumnsStream,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    return Column(
                      children: (snapshot.data as List<ColumnEntity>)
                          .map((column) => Card(
                                elevation: 5,
                                child: ListTile(
                                  title: Text("${column.title} - ${column.id}"),
                                  subtitle: Text(column.description),
                                ),
                              ))
                          .toList(),
                    );
                  }
                  return Container();
                },
              )
            ],
          ),
        );
      }),
      floatingActionButton: Column(
        spacing: 16,
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          FloatingActionButton(
            onPressed: widget.presenter.createColumn,
            tooltip: 'Increment',
            child: const Icon(Icons.add),
          ),
          FloatingActionButton(
            onPressed: widget.presenter.listColumns,
            tooltip: 'Increment',
            child: const Icon(Icons.view_column),
          ),
        ],
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.endFloat,
    );
  }
}
