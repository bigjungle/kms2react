import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './query-common-10-sdm-suffix.reducer';
import { IQueryCommon10SdmSuffix } from 'app/shared/model/query-common-10-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IQueryCommon10SdmSuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IQueryCommon10SdmSuffixUpdateState {
  isNew: boolean;
}

export class QueryCommon10SdmSuffixUpdate extends React.Component<IQueryCommon10SdmSuffixUpdateProps, IQueryCommon10SdmSuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    values.queryDate = convertDateTimeToServer(values.queryDate);

    if (errors.length === 0) {
      const { queryCommon10Entity } = this.props;
      const entity = {
        ...queryCommon10Entity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/query-common-10-sdm-suffix');
  };

  render() {
    const { queryCommon10Entity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="kmsApp.queryCommon10.home.createOrEditLabel">
              <Translate contentKey="kmsApp.queryCommon10.home.createOrEditLabel">Create or edit a QueryCommon10</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : queryCommon10Entity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="query-common-10-sdm-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="queryNameLabel" for="queryName">
                    <Translate contentKey="kmsApp.queryCommon10.queryName">Query Name</Translate>
                  </Label>
                  <AvField
                    id="query-common-10-sdm-suffix-queryName"
                    type="text"
                    name="queryName"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="queryDateLabel" for="queryDate">
                    <Translate contentKey="kmsApp.queryCommon10.queryDate">Query Date</Translate>
                  </Label>
                  <AvInput
                    id="query-common-10-sdm-suffix-queryDate"
                    type="datetime-local"
                    className="form-control"
                    name="queryDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.queryCommon10Entity.queryDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="queryUserLabel" for="queryUser">
                    <Translate contentKey="kmsApp.queryCommon10.queryUser">Query User</Translate>
                  </Label>
                  <AvField
                    id="query-common-10-sdm-suffix-queryUser"
                    type="text"
                    name="queryUser"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col01Label" for="col01">
                    <Translate contentKey="kmsApp.queryCommon10.col01">Col 01</Translate>
                  </Label>
                  <AvField
                    id="query-common-10-sdm-suffix-col01"
                    type="text"
                    name="col01"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col02Label" for="col02">
                    <Translate contentKey="kmsApp.queryCommon10.col02">Col 02</Translate>
                  </Label>
                  <AvField
                    id="query-common-10-sdm-suffix-col02"
                    type="text"
                    name="col02"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col03Label" for="col03">
                    <Translate contentKey="kmsApp.queryCommon10.col03">Col 03</Translate>
                  </Label>
                  <AvField
                    id="query-common-10-sdm-suffix-col03"
                    type="text"
                    name="col03"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col04Label" for="col04">
                    <Translate contentKey="kmsApp.queryCommon10.col04">Col 04</Translate>
                  </Label>
                  <AvField
                    id="query-common-10-sdm-suffix-col04"
                    type="text"
                    name="col04"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col05Label" for="col05">
                    <Translate contentKey="kmsApp.queryCommon10.col05">Col 05</Translate>
                  </Label>
                  <AvField
                    id="query-common-10-sdm-suffix-col05"
                    type="text"
                    name="col05"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col06Label" for="col06">
                    <Translate contentKey="kmsApp.queryCommon10.col06">Col 06</Translate>
                  </Label>
                  <AvField
                    id="query-common-10-sdm-suffix-col06"
                    type="text"
                    name="col06"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col07Label" for="col07">
                    <Translate contentKey="kmsApp.queryCommon10.col07">Col 07</Translate>
                  </Label>
                  <AvField
                    id="query-common-10-sdm-suffix-col07"
                    type="text"
                    name="col07"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col08Label" for="col08">
                    <Translate contentKey="kmsApp.queryCommon10.col08">Col 08</Translate>
                  </Label>
                  <AvField
                    id="query-common-10-sdm-suffix-col08"
                    type="text"
                    name="col08"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col09Label" for="col09">
                    <Translate contentKey="kmsApp.queryCommon10.col09">Col 09</Translate>
                  </Label>
                  <AvField
                    id="query-common-10-sdm-suffix-col09"
                    type="text"
                    name="col09"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col10Label" for="col10">
                    <Translate contentKey="kmsApp.queryCommon10.col10">Col 10</Translate>
                  </Label>
                  <AvField
                    id="query-common-10-sdm-suffix-col10"
                    type="text"
                    name="col10"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/query-common-10-sdm-suffix" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  queryCommon10Entity: storeState.queryCommon10.entity,
  loading: storeState.queryCommon10.loading,
  updating: storeState.queryCommon10.updating,
  updateSuccess: storeState.queryCommon10.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(QueryCommon10SdmSuffixUpdate);
