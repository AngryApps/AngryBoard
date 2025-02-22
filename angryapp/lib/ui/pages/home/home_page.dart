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
        title: Text("Alooooo"),
      ),
      body: Builder(builder: (_) {
        handleLoading(context, widget.presenter.isLoadingStream);

        widget.presenter.messageStream.listen((event) {
          setState(() {
            message = event;
          });
        });
        return Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Text(message.title),
              Text(
                message.toJson(),
                style: Theme.of(context).textTheme.headlineMedium,
              ),
            ],
          ),
        );
      }),
      floatingActionButton: FloatingActionButton(
        onPressed: widget.presenter.createColumn,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ),
    );
  }
}
