import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import QueryCommon10SdmSuffix from './query-common-10-sdm-suffix';
import QueryCommon10SdmSuffixDetail from './query-common-10-sdm-suffix-detail';
import QueryCommon10SdmSuffixUpdate from './query-common-10-sdm-suffix-update';
import QueryCommon10SdmSuffixDeleteDialog from './query-common-10-sdm-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={QueryCommon10SdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={QueryCommon10SdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={QueryCommon10SdmSuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={QueryCommon10SdmSuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={QueryCommon10SdmSuffixDeleteDialog} />
  </>
);

export default Routes;
