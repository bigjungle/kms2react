import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './para-user-sdm-suffix.reducer';
import { IParaUserSdmSuffix } from 'app/shared/model/para-user-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IParaUserSdmSuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ParaUserSdmSuffixDetail extends React.Component<IParaUserSdmSuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { paraUserEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="kmsApp.paraUser.detail.title">ParaUser</Translate> [<b>{paraUserEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="userId">
                <Translate contentKey="kmsApp.paraUser.userId">User Id</Translate>
              </span>
              <UncontrolledTooltip target="userId">
                <Translate contentKey="kmsApp.paraUser.help.userId" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraUserEntity.userId}</dd>
            <dt>
              <span id="name">
                <Translate contentKey="kmsApp.paraUser.name">Name</Translate>
              </span>
              <UncontrolledTooltip target="name">
                <Translate contentKey="kmsApp.paraUser.help.name" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraUserEntity.name}</dd>
            <dt>
              <span id="serialNumber">
                <Translate contentKey="kmsApp.paraUser.serialNumber">Serial Number</Translate>
              </span>
              <UncontrolledTooltip target="serialNumber">
                <Translate contentKey="kmsApp.paraUser.help.serialNumber" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraUserEntity.serialNumber}</dd>
            <dt>
              <span id="descString">
                <Translate contentKey="kmsApp.paraUser.descString">Desc String</Translate>
              </span>
              <UncontrolledTooltip target="descString">
                <Translate contentKey="kmsApp.paraUser.help.descString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraUserEntity.descString}</dd>
            <dt>
              <span id="jobId">
                <Translate contentKey="kmsApp.paraUser.jobId">Job Id</Translate>
              </span>
              <UncontrolledTooltip target="jobId">
                <Translate contentKey="kmsApp.paraUser.help.jobId" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraUserEntity.jobId}</dd>
            <dt>
              <span id="firstName">
                <Translate contentKey="kmsApp.paraUser.firstName">First Name</Translate>
              </span>
              <UncontrolledTooltip target="firstName">
                <Translate contentKey="kmsApp.paraUser.help.firstName" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraUserEntity.firstName}</dd>
            <dt>
              <span id="lastName">
                <Translate contentKey="kmsApp.paraUser.lastName">Last Name</Translate>
              </span>
              <UncontrolledTooltip target="lastName">
                <Translate contentKey="kmsApp.paraUser.help.lastName" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraUserEntity.lastName}</dd>
            <dt>
              <span id="mobile">
                <Translate contentKey="kmsApp.paraUser.mobile">Mobile</Translate>
              </span>
              <UncontrolledTooltip target="mobile">
                <Translate contentKey="kmsApp.paraUser.help.mobile" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraUserEntity.mobile}</dd>
            <dt>
              <span id="mail">
                <Translate contentKey="kmsApp.paraUser.mail">Mail</Translate>
              </span>
              <UncontrolledTooltip target="mail">
                <Translate contentKey="kmsApp.paraUser.help.mail" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraUserEntity.mail}</dd>
            <dt>
              <span id="usingFlag">
                <Translate contentKey="kmsApp.paraUser.usingFlag">Using Flag</Translate>
              </span>
              <UncontrolledTooltip target="usingFlag">
                <Translate contentKey="kmsApp.paraUser.help.usingFlag" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraUserEntity.usingFlag ? 'true' : 'false'}</dd>
            <dt>
              <span id="remarks">
                <Translate contentKey="kmsApp.paraUser.remarks">Remarks</Translate>
              </span>
              <UncontrolledTooltip target="remarks">
                <Translate contentKey="kmsApp.paraUser.help.remarks" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraUserEntity.remarks}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraUser.createdUser">Created User</Translate>
            </dt>
            <dd>{paraUserEntity.createdUserName ? paraUserEntity.createdUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraUser.modifiedUser">Modified User</Translate>
            </dt>
            <dd>{paraUserEntity.modifiedUserName ? paraUserEntity.modifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraUser.verifiedUser">Verified User</Translate>
            </dt>
            <dd>{paraUserEntity.verifiedUserName ? paraUserEntity.verifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraUser.paraRole">Para Role</Translate>
            </dt>
            <dd>
              {paraUserEntity.paraRoles
                ? paraUserEntity.paraRoles.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.name}</a>
                      {i === paraUserEntity.paraRoles.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/para-user-sdm-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/para-user-sdm-suffix/${paraUserEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ paraUser }: IRootState) => ({
  paraUserEntity: paraUser.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ParaUserSdmSuffixDetail);
