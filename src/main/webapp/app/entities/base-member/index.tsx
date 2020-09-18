import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BaseMember from './base-member';
import BaseMemberDetail from './base-member-detail';
import BaseMemberUpdate from './base-member-update';
import BaseMemberDeleteDialog from './base-member-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BaseMemberUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BaseMemberUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BaseMemberDetail} />
      <ErrorBoundaryRoute path={match.url} component={BaseMember} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BaseMemberDeleteDialog} />
  </>
);

export default Routes;
