import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Base from './base';
import BaseDetail from './base-detail';
import BaseUpdate from './base-update';
import BaseDeleteDialog from './base-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BaseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BaseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BaseDetail} />
      <ErrorBoundaryRoute path={match.url} component={Base} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BaseDeleteDialog} />
  </>
);

export default Routes;
