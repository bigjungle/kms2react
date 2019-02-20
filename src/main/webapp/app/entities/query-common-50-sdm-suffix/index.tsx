import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import QueryCommon50SdmSuffix from './query-common-50-sdm-suffix';
import QueryCommon50SdmSuffixDetail from './query-common-50-sdm-suffix-detail';
import QueryCommon50SdmSuffixUpdate from './query-common-50-sdm-suffix-update';
import QueryCommon50SdmSuffixDeleteDialog from './query-common-50-sdm-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={QueryCommon50SdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={QueryCommon50SdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={QueryCommon50SdmSuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={QueryCommon50SdmSuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={QueryCommon50SdmSuffixDeleteDialog} />
  </>
);

export default Routes;
