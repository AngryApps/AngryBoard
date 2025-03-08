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
                        child: ReorderableListView.builder(
                          onReorder: (oldIndex, newIndex) => _onReorderColumn(
                            oldIndex,
                            newIndex,
                            columns,
                          ),
                          proxyDecorator: (child, index, animation) {
                            return SizedBox(
                              width: MediaQuery.of(context).size.width * 0.8,
                              child: Column(
                                children: [
                                  Expanded(
                                    child: _buildColumn(
                                      columns[index],
                                      Color.fromRGBO(121, 86, 72, 1),
                                      isProxyColumn: true,
                                    ),
                                  ),
                                ],
                              ),
                            );
                          },
                          scrollDirection: Axis.horizontal,
                          itemCount: columns.length,
                          itemBuilder: (BuildContext context, int index) =>
                              _buildColumn(
                            columns[index],
                            Color.fromRGBO(215, 204, 200, 1),
                          ),
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
    );
  }

  Widget _buildColumn(ColumnEntity column, Color columnColor,
      {bool isProxyColumn = false}) {
    return SizedBox(
      key: ValueKey(column),
      width: MediaQuery.of(context).size.width * 0.8,
      child: Card(
        color: columnColor,
        elevation: 5,
        child: Column(
          children: [
            _createListTile(
              title: column.title,
              subtitle: column.description,
              isProxy: isProxyColumn,
            ),
            Expanded(
              child: ReorderableListView.builder(
                onReorder: (oldIndex, newIndex) => _onReorderCard(
                  oldIndex,
                  newIndex,
                  column,
                ),
                proxyDecorator: (child, index, animation) {
                  final card = column.cards[index];
                  return Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 4.0),
                    child: Card(
                      elevation: 8,
                      color: Color.fromRGBO(121, 86, 72, 1),
                      child: _createListTile(
                        title: card.title,
                        subtitle: card.description,
                        isProxy: true,
                      ),
                    ),
                  );
                },
                itemCount: column.cards.length,
                itemBuilder: ((context, index) {
                  final card = column.cards[index];
                  return Padding(
                    key: ValueKey(card),
                    padding: const EdgeInsets.symmetric(horizontal: 4.0),
                    child: Card(
                      elevation: 4,
                      child: _createListTile(
                        title: card.title,
                        subtitle: card.description,
                      ),
                    ),
                  );
                }),
              ),
            ),
            _createListTile(
              title: "Add Card",
              alignment: TextAlign.center,
              onTap: () {},
            ),
          ],
        ),
      ),
    );
  }

  Widget _createListTile({
    String? title,
    String? subtitle,
    bool isProxy = false,
    TextAlign alignment = TextAlign.start,
    void Function()? onTap,
  }) {
    return ListTile(
      title: title != null
          ? Text(
              title,
              textAlign: alignment,
              style: TextStyle(
                color: isProxy ? Colors.white : Color.fromRGBO(113, 95, 89, 1),
                fontWeight: FontWeight.bold,
              ),
            )
          : null,
      subtitle: subtitle != null
          ? Text(
              subtitle,
              style: TextStyle(
                color: isProxy ? Colors.white : Color.fromRGBO(113, 95, 89, 1),
              ),
            )
          : null,
      onTap: onTap,
    );
  }

  void _onReorderCard(int oldIndex, int newIndex, ColumnEntity column) {
    setState(() {
      if (oldIndex < newIndex) {
        newIndex -= 1;
      }
      final card = column.cards.removeAt(oldIndex);
      column.cards.insert(newIndex, card);
    });
  }

  void _onReorderColumn(int oldIndex, int newIndex, List<ColumnEntity> column) {
    setState(() {
      if (oldIndex < newIndex) {
        newIndex -= 1;
      }
      final card = column.removeAt(oldIndex);
      column.insert(newIndex, card);
    });
  }
}
