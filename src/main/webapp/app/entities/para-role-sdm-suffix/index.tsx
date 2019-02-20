import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ParaRoleSdmSuffix from './para-role-sdm-suffix';
import ParaRoleSdmSuffixDetail from './para-role-sdm-suffix-detail';
import ParaRoleSdmSuffixUpdate from './para-role-sdm-suffix-update';
import ParaRoleSdmSuffixDeleteDialog from './para-role-sdm-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParaRoleSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParaRoleSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParaRoleSdmSuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={ParaRoleSdmSuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ParaRoleSdmSuffixDeleteDialog} />
  </>
);

export default Routes;
