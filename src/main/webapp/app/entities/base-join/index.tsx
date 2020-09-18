import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BaseJoin from './base-join';
import BaseJoinDetail from './base-join-detail';
import BaseJoinUpdate from './base-join-update';
import BaseJoinDeleteDialog from './base-join-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BaseJoinUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BaseJoinUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BaseJoinDetail} />
      <ErrorBoundaryRoute path={match.url} component={BaseJoin} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BaseJoinDeleteDialog} />
  </>
);

export default Routes;
