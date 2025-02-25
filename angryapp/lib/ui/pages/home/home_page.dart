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
  @override
  void initState() {
    super.initState();
    widget.presenter.listColumns();
  }

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

        widget.presenter.createColumnStream.listen((event) {
          widget.presenter.listColumns();
        });
        return Padding(
          padding: const EdgeInsets.all(8.0),
          child: SizedBox(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height,
            child: StreamBuilder(
              stream: widget.presenter.listColumnsStream,
              builder: (context, snapshot) {
                if (snapshot.hasData) {
                  final columns = snapshot.data as List<ColumnEntity>;
                  return Column(
                    children: [
                      Expanded(
                        child: ListView.builder(
                          scrollDirection: Axis.horizontal,
                          itemCount: columns.length,
                          itemBuilder: (BuildContext context, int index) =>
                              _buildColumn(columns[index]),
                        ),
                      ),
                    ],
                  );
                }
                return Center(
                  child: Text(
                    "Nenhuma coluna cadastrada",
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                      fontSize: 18,
                    ),
                  ),
                );
              },
            ),
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

  _buildColumn(ColumnEntity column) {
    return SizedBox(
      width: MediaQuery.of(context).size.width * 0.8,
      child: Card(
        color: Color.fromRGBO(215, 204, 200, 1),
        elevation: 10,
        child: ListTile(
          title: Text(
            column.title,
            style: TextStyle(
              color: Color.fromRGBO(113, 95, 89, 1),
            ),
          ),
          subtitle: Text(
            column.description,
            style: TextStyle(
              color: Color.fromRGBO(161, 136, 127, 1),
            ),
          ),
        ),
      ),
    );
  }
}
