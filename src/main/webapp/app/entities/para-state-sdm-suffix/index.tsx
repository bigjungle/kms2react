import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ParaStateSdmSuffix from './para-state-sdm-suffix';
import ParaStateSdmSuffixDetail from './para-state-sdm-suffix-detail';
import ParaStateSdmSuffixUpdate from './para-state-sdm-suffix-update';
import ParaStateSdmSuffixDeleteDialog from './para-state-sdm-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParaStateSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParaStateSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParaStateSdmSuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={ParaStateSdmSuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ParaStateSdmSuffixDeleteDialog} />
  </>
);

export default Routes;
