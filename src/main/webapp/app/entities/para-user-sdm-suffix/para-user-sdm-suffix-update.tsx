import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntities as getParaUsers } from 'app/entities/para-user-sdm-suffix/para-user-sdm-suffix.reducer';
import { IParaRoleSdmSuffix } from 'app/shared/model/para-role-sdm-suffix.model';
import { getEntities as getParaRoles } from 'app/entities/para-role-sdm-suffix/para-role-sdm-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './para-user-sdm-suffix.reducer';
import { IParaUserSdmSuffix } from 'app/shared/model/para-user-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IParaUserSdmSuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IParaUserSdmSuffixUpdateState {
  isNew: boolean;
  idsparaRole: any[];
  createdUserId: string;
  modifiedUserId: string;
  verifiedUserId: string;
}

export class ParaUserSdmSuffixUpdate extends React.Component<IParaUserSdmSuffixUpdateProps, IParaUserSdmSuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsparaRole: [],
      createdUserId: '0',
      modifiedUserId: '0',
      verifiedUserId: '0',
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
    this.props.getParaRoles();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { paraUserEntity } = this.props;
      const entity = {
        ...paraUserEntity,
        ...values,
        paraRoles: mapIdList(values.paraRoles)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/para-user-sdm-suffix');
  };

  render() {
    const { paraUserEntity, paraUsers, paraRoles, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="kmsApp.paraUser.home.createOrEditLabel">
              <Translate contentKey="kmsApp.paraUser.home.createOrEditLabel">Create or edit a ParaUser</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : paraUserEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="para-user-sdm-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="userIdLabel" for="userId">
                    <Translate contentKey="kmsApp.paraUser.userId">User Id</Translate>
                  </Label>
                  <AvField
                    id="para-user-sdm-suffix-userId"
                    type="text"
                    name="userId"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="userIdLabel">
                    <Translate contentKey="kmsApp.paraUser.help.userId" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="kmsApp.paraUser.name">Name</Translate>
                  </Label>
                  <AvField
                    id="para-user-sdm-suffix-name"
                    type="text"
                    name="name"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="nameLabel">
                    <Translate contentKey="kmsApp.paraUser.help.name" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="serialNumberLabel" for="serialNumber">
                    <Translate contentKey="kmsApp.paraUser.serialNumber">Serial Number</Translate>
                  </Label>
                  <AvField
                    id="para-user-sdm-suffix-serialNumber"
                    type="text"
                    name="serialNumber"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="serialNumberLabel">
                    <Translate contentKey="kmsApp.paraUser.help.serialNumber" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="descStringLabel" for="descString">
                    <Translate contentKey="kmsApp.paraUser.descString">Desc String</Translate>
                  </Label>
                  <AvField
                    id="para-user-sdm-suffix-descString"
                    type="text"
                    name="descString"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="descStringLabel">
                    <Translate contentKey="kmsApp.paraUser.help.descString" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="jobIdLabel" for="jobId">
                    <Translate contentKey="kmsApp.paraUser.jobId">Job Id</Translate>
                  </Label>
                  <AvField
                    id="para-user-sdm-suffix-jobId"
                    type="text"
                    name="jobId"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="jobIdLabel">
                    <Translate contentKey="kmsApp.paraUser.help.jobId" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="firstNameLabel" for="firstName">
                    <Translate contentKey="kmsApp.paraUser.firstName">First Name</Translate>
                  </Label>
                  <AvField
                    id="para-user-sdm-suffix-firstName"
                    type="text"
                    name="firstName"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="firstNameLabel">
                    <Translate contentKey="kmsApp.paraUser.help.firstName" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="lastNameLabel" for="lastName">
                    <Translate contentKey="kmsApp.paraUser.lastName">Last Name</Translate>
                  </Label>
                  <AvField
                    id="para-user-sdm-suffix-lastName"
                    type="text"
                    name="lastName"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="lastNameLabel">
                    <Translate contentKey="kmsApp.paraUser.help.lastName" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="mobileLabel" for="mobile">
                    <Translate contentKey="kmsApp.paraUser.mobile">Mobile</Translate>
                  </Label>
                  <AvField
                    id="para-user-sdm-suffix-mobile"
                    type="text"
                    name="mobile"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="mobileLabel">
                    <Translate contentKey="kmsApp.paraUser.help.mobile" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="mailLabel" for="mail">
                    <Translate contentKey="kmsApp.paraUser.mail">Mail</Translate>
                  </Label>
                  <AvField
                    id="para-user-sdm-suffix-mail"
                    type="text"
                    name="mail"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="mailLabel">
                    <Translate contentKey="kmsApp.paraUser.help.mail" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="usingFlagLabel" check>
                    <AvInput id="para-user-sdm-suffix-usingFlag" type="checkbox" className="form-control" name="usingFlag" />
                    <Translate contentKey="kmsApp.paraUser.usingFlag">Using Flag</Translate>
                  </Label>
                  <UncontrolledTooltip target="usingFlagLabel">
                    <Translate contentKey="kmsApp.paraUser.help.usingFlag" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="remarksLabel" for="remarks">
                    <Translate contentKey="kmsApp.paraUser.remarks">Remarks</Translate>
                  </Label>
                  <AvField
                    id="para-user-sdm-suffix-remarks"
                    type="text"
                    name="remarks"
                    validate={{
                      maxLength: { value: 1000, errorMessage: translate('entity.validation.maxlength', { max: 1000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="remarksLabel">
                    <Translate contentKey="kmsApp.paraUser.help.remarks" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label for="createdUser.name">
                    <Translate contentKey="kmsApp.paraUser.createdUser">Created User</Translate>
                  </Label>
                  <AvInput id="para-user-sdm-suffix-createdUser" type="select" className="form-control" name="createdUserId">
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
                    <Translate contentKey="kmsApp.paraUser.modifiedUser">Modified User</Translate>
                  </Label>
                  <AvInput id="para-user-sdm-suffix-modifiedUser" type="select" className="form-control" name="modifiedUserId">
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
                    <Translate contentKey="kmsApp.paraUser.verifiedUser">Verified User</Translate>
                  </Label>
                  <AvInput id="para-user-sdm-suffix-verifiedUser" type="select" className="form-control" name="verifiedUserId">
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
                  <Label for="paraRoles">
                    <Translate contentKey="kmsApp.paraUser.paraRole">Para Role</Translate>
                  </Label>
                  <AvInput
                    id="para-user-sdm-suffix-paraRole"
                    type="select"
                    multiple
                    className="form-control"
                    name="paraRoles"
                    value={paraUserEntity.paraRoles && paraUserEntity.paraRoles.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {paraRoles
                      ? paraRoles.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/para-user-sdm-suffix" replace color="info">
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
  paraRoles: storeState.paraRole.entities,
  paraUserEntity: storeState.paraUser.entity,
  loading: storeState.paraUser.loading,
  updating: storeState.paraUser.updating,
  updateSuccess: storeState.paraUser.updateSuccess
});

const mapDispatchToProps = {
  getParaUsers,
  getParaRoles,
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
)(ParaUserSdmSuffixUpdate);
