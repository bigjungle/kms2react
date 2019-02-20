import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import KmsInfoSdmSuffix from './kms-info-sdm-suffix';
import KmsInfoSdmSuffixDetail from './kms-info-sdm-suffix-detail';
import KmsInfoSdmSuffixUpdate from './kms-info-sdm-suffix-update';
import KmsInfoSdmSuffixDeleteDialog from './kms-info-sdm-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={KmsInfoSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={KmsInfoSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={KmsInfoSdmSuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={KmsInfoSdmSuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={KmsInfoSdmSuffixDeleteDialog} />
  </>
);

export default Routes;
