import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VerifyRecSdmSuffix from './verify-rec-sdm-suffix';
import VerifyRecSdmSuffixDetail from './verify-rec-sdm-suffix-detail';
import VerifyRecSdmSuffixUpdate from './verify-rec-sdm-suffix-update';
import VerifyRecSdmSuffixDeleteDialog from './verify-rec-sdm-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VerifyRecSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VerifyRecSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VerifyRecSdmSuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={VerifyRecSdmSuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={VerifyRecSdmSuffixDeleteDialog} />
  </>
);

export default Routes;
