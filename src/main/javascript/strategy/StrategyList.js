import React from 'react';
import {ListItem, List} from 'material-ui/List';
import Divider from 'material-ui/Divider';

export default class StrategyList extends React.Component {
  constructor(props) {
    super(props);
  }

  handleListItemCLick = (strategyId) => {
    this.props.parent.setState({ selectedStrategyId: strategyId});
    this.props.parent.gotoPage(1);
  }

  render() {
    var rows = [];
    var strategyCount = this.props.strategies.length;
		for (var i = 0; i < strategyCount; i++) {
			var strategy = this.props.strategies[i];
      rows.push(
        <div>
        <ListItem
        primaryText={strategy.name}
        onTouchTap={this.handleListItemCLick.bind(this, strategy.id)}
        />
        <Divider />
        </div>
      );
    };

    return (
      <List>
      {rows}
      </List>
    );
  }

};
