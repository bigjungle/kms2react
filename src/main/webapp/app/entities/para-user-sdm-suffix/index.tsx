import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ParaUserSdmSuffix from './para-user-sdm-suffix';
import ParaUserSdmSuffixDetail from './para-user-sdm-suffix-detail';
import ParaUserSdmSuffixUpdate from './para-user-sdm-suffix-update';
import ParaUserSdmSuffixDeleteDialog from './para-user-sdm-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParaUserSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParaUserSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParaUserSdmSuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={ParaUserSdmSuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ParaUserSdmSuffixDeleteDialog} />
  </>
);

export default Routes;
