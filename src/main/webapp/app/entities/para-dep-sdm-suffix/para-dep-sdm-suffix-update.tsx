import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IParaUserSdmSuffix } from 'app/shared/model/para-user-sdm-suffix.model';
import { getEntities as getParaUsers } from 'app/entities/para-user-sdm-suffix/para-user-sdm-suffix.reducer';
import { getEntities as getParaDeps } from 'app/entities/para-dep-sdm-suffix/para-dep-sdm-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './para-dep-sdm-suffix.reducer';
import { IParaDepSdmSuffix } from 'app/shared/model/para-dep-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IParaDepSdmSuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IParaDepSdmSuffixUpdateState {
  isNew: boolean;
  createdUserId: string;
  modifiedUserId: string;
  verifiedUserId: string;
  parentId: string;
}

export class ParaDepSdmSuffixUpdate extends React.Component<IParaDepSdmSuffixUpdateProps, IParaDepSdmSuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      createdUserId: '0',
      modifiedUserId: '0',
      verifiedUserId: '0',
      parentId: '0',
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

    this.props.getParaUsers();
    this.props.getParaDeps();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { paraDepEntity } = this.props;
      const entity = {
        ...paraDepEntity,
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
    this.props.history.push('/entity/para-dep-sdm-suffix');
  };

  render() {
    const { paraDepEntity, paraUsers, paraDeps, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="kmsApp.paraDep.home.createOrEditLabel">
              <Translate contentKey="kmsApp.paraDep.home.createOrEditLabel">Create or edit a ParaDep</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : paraDepEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="para-dep-sdm-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="kmsApp.paraDep.name">Name</Translate>
                  </Label>
                  <AvField
                    id="para-dep-sdm-suffix-name"
                    type="text"
                    name="name"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="nameLabel">
                    <Translate contentKey="kmsApp.paraDep.help.name" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="serialNumberLabel" for="serialNumber">
                    <Translate contentKey="kmsApp.paraDep.serialNumber">Serial Number</Translate>
                  </Label>
                  <AvField
                    id="para-dep-sdm-suffix-serialNumber"
                    type="text"
                    name="serialNumber"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="serialNumberLabel">
                    <Translate contentKey="kmsApp.paraDep.help.serialNumber" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="descStringLabel" for="descString">
                    <Translate contentKey="kmsApp.paraDep.descString">Desc String</Translate>
                  </Label>
                  <AvField
                    id="para-dep-sdm-suffix-descString"
                    type="text"
                    name="descString"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="descStringLabel">
                    <Translate contentKey="kmsApp.paraDep.help.descString" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="telLabel" for="tel">
                    <Translate contentKey="kmsApp.paraDep.tel">Tel</Translate>
                  </Label>
                  <AvField
                    id="para-dep-sdm-suffix-tel"
                    type="text"
                    name="tel"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="telLabel">
                    <Translate contentKey="kmsApp.paraDep.help.tel" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="addressLabel" for="address">
                    <Translate contentKey="kmsApp.paraDep.address">Address</Translate>
                  </Label>
                  <AvField
                    id="para-dep-sdm-suffix-address"
                    type="text"
                    name="address"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="addressLabel">
                    <Translate contentKey="kmsApp.paraDep.help.address" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="remarksLabel" for="remarks">
                    <Translate contentKey="kmsApp.paraDep.remarks">Remarks</Translate>
                  </Label>
                  <AvField
                    id="para-dep-sdm-suffix-remarks"
                    type="text"
                    name="remarks"
                    validate={{
                      maxLength: { value: 1000, errorMessage: translate('entity.validation.maxlength', { max: 1000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="remarksLabel">
                    <Translate contentKey="kmsApp.paraDep.help.remarks" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label for="createdUser.name">
                    <Translate contentKey="kmsApp.paraDep.createdUser">Created User</Translate>
                  </Label>
                  <AvInput id="para-dep-sdm-suffix-createdUser" type="select" className="form-control" name="createdUserId">
                    <option value="" key="0" />
                    {paraUsers
                      ? paraUsers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="modifiedUser.name">
                    <Translate contentKey="kmsApp.paraDep.modifiedUser">Modified User</Translate>
                  </Label>
                  <AvInput id="para-dep-sdm-suffix-modifiedUser" type="select" className="form-control" name="modifiedUserId">
                    <option value="" key="0" />
                    {paraUsers
                      ? paraUsers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="verifiedUser.name">
                    <Translate contentKey="kmsApp.paraDep.verifiedUser">Verified User</Translate>
                  </Label>
                  <AvInput id="para-dep-sdm-suffix-verifiedUser" type="select" className="form-control" name="verifiedUserId">
                    <option value="" key="0" />
                    {paraUsers
                      ? paraUsers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="parent.name">
                    <Translate contentKey="kmsApp.paraDep.parent">Parent</Translate>
                  </Label>
                  <AvInput id="para-dep-sdm-suffix-parent" type="select" className="form-control" name="parentId">
                    <option value="" key="0" />
                    {paraDeps
                      ? paraDeps.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/para-dep-sdm-suffix" replace color="info">
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
  paraUsers: storeState.paraUser.entities,
  paraDeps: storeState.paraDep.entities,
  paraDepEntity: storeState.paraDep.entity,
  loading: storeState.paraDep.loading,
  updating: storeState.paraDep.updating,
  updateSuccess: storeState.paraDep.updateSuccess
});

const mapDispatchToProps = {
  getParaUsers,
  getParaDeps,
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
)(ParaDepSdmSuffixUpdate);
