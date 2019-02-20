import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ParaTypeSdmSuffix from './para-type-sdm-suffix';
import ParaTypeSdmSuffixDetail from './para-type-sdm-suffix-detail';
import ParaTypeSdmSuffixUpdate from './para-type-sdm-suffix-update';
import ParaTypeSdmSuffixDeleteDialog from './para-type-sdm-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParaTypeSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParaTypeSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParaTypeSdmSuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={ParaTypeSdmSuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ParaTypeSdmSuffixDeleteDialog} />
  </>
);

export default Routes;
