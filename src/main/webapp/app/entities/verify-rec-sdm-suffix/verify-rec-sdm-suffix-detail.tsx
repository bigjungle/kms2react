import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './verify-rec-sdm-suffix.reducer';
import { IVerifyRecSdmSuffix } from 'app/shared/model/verify-rec-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVerifyRecSdmSuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class VerifyRecSdmSuffixDetail extends React.Component<IVerifyRecSdmSuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { verifyRecEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="kmsApp.verifyRec.detail.title">VerifyRec</Translate> [<b>{verifyRecEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="kmsApp.verifyRec.name">Name</Translate>
              </span>
              <UncontrolledTooltip target="name">
                <Translate contentKey="kmsApp.verifyRec.help.name" />
              </UncontrolledTooltip>
            </dt>
            <dd>{verifyRecEntity.name}</dd>
            <dt>
              <span id="descString">
                <Translate contentKey="kmsApp.verifyRec.descString">Desc String</Translate>
              </span>
              <UncontrolledTooltip target="descString">
                <Translate contentKey="kmsApp.verifyRec.help.descString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{verifyRecEntity.descString}</dd>
            <dt>
              <span id="verifyResult">
                <Translate contentKey="kmsApp.verifyRec.verifyResult">Verify Result</Translate>
              </span>
              <UncontrolledTooltip target="verifyResult">
                <Translate contentKey="kmsApp.verifyRec.help.verifyResult" />
              </UncontrolledTooltip>
            </dt>
            <dd>{verifyRecEntity.verifyResult ? 'true' : 'false'}</dd>
            <dt>
              <span id="verifyScore">
                <Translate contentKey="kmsApp.verifyRec.verifyScore">Verify Score</Translate>
              </span>
              <UncontrolledTooltip target="verifyScore">
                <Translate contentKey="kmsApp.verifyRec.help.verifyScore" />
              </UncontrolledTooltip>
            </dt>
            <dd>{verifyRecEntity.verifyScore}</dd>
            <dt>
              <span id="remarks">
                <Translate contentKey="kmsApp.verifyRec.remarks">Remarks</Translate>
              </span>
              <UncontrolledTooltip target="remarks">
                <Translate contentKey="kmsApp.verifyRec.help.remarks" />
              </UncontrolledTooltip>
            </dt>
            <dd>{verifyRecEntity.remarks}</dd>
            <dt>
              <span id="createTime">
                <Translate contentKey="kmsApp.verifyRec.createTime">Create Time</Translate>
              </span>
              <UncontrolledTooltip target="createTime">
                <Translate contentKey="kmsApp.verifyRec.help.createTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={verifyRecEntity.createTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="modifyTime">
                <Translate contentKey="kmsApp.verifyRec.modifyTime">Modify Time</Translate>
              </span>
              <UncontrolledTooltip target="modifyTime">
                <Translate contentKey="kmsApp.verifyRec.help.modifyTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={verifyRecEntity.modifyTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="kmsApp.verifyRec.createdUser">Created User</Translate>
            </dt>
            <dd>{verifyRecEntity.createdUserName ? verifyRecEntity.createdUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.verifyRec.modifiedUser">Modified User</Translate>
            </dt>
            <dd>{verifyRecEntity.modifiedUserName ? verifyRecEntity.modifiedUserName : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/verify-rec-sdm-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/verify-rec-sdm-suffix/${verifyRecEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ verifyRec }: IRootState) => ({
  verifyRecEntity: verifyRec.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VerifyRecSdmSuffixDetail);
