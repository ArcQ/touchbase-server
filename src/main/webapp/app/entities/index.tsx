import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Base from './base';
import Chat from './chat';
import Mission from './mission';
import BaseMember from './base-member';
import BaseJoin from './base-join';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}base`} component={Base} />
      <ErrorBoundaryRoute path={`${match.url}chat`} component={Chat} />
      <ErrorBoundaryRoute path={`${match.url}mission`} component={Mission} />
      <ErrorBoundaryRoute path={`${match.url}base-member`} component={BaseMember} />
      <ErrorBoundaryRoute path={`${match.url}base-join`} component={BaseJoin} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
