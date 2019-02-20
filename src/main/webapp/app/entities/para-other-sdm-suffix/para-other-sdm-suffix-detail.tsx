import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './para-other-sdm-suffix.reducer';
import { IParaOtherSdmSuffix } from 'app/shared/model/para-other-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IParaOtherSdmSuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ParaOtherSdmSuffixDetail extends React.Component<IParaOtherSdmSuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { paraOtherEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="kmsApp.paraOther.detail.title">ParaOther</Translate> [<b>{paraOtherEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="kmsApp.paraOther.name">Name</Translate>
              </span>
              <UncontrolledTooltip target="name">
                <Translate contentKey="kmsApp.paraOther.help.name" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraOtherEntity.name}</dd>
            <dt>
              <span id="serialNumber">
                <Translate contentKey="kmsApp.paraOther.serialNumber">Serial Number</Translate>
              </span>
              <UncontrolledTooltip target="serialNumber">
                <Translate contentKey="kmsApp.paraOther.help.serialNumber" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraOtherEntity.serialNumber}</dd>
            <dt>
              <span id="sortString">
                <Translate contentKey="kmsApp.paraOther.sortString">Sort String</Translate>
              </span>
              <UncontrolledTooltip target="sortString">
                <Translate contentKey="kmsApp.paraOther.help.sortString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraOtherEntity.sortString}</dd>
            <dt>
              <span id="descString">
                <Translate contentKey="kmsApp.paraOther.descString">Desc String</Translate>
              </span>
              <UncontrolledTooltip target="descString">
                <Translate contentKey="kmsApp.paraOther.help.descString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraOtherEntity.descString}</dd>
            <dt>
              <span id="paraOtherValueType">
                <Translate contentKey="kmsApp.paraOther.paraOtherValueType">Para Other Value Type</Translate>
              </span>
              <UncontrolledTooltip target="paraOtherValueType">
                <Translate contentKey="kmsApp.paraOther.help.paraOtherValueType" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraOtherEntity.paraOtherValueType}</dd>
            <dt>
              <span id="paraValueSt">
                <Translate contentKey="kmsApp.paraOther.paraValueSt">Para Value St</Translate>
              </span>
              <UncontrolledTooltip target="paraValueSt">
                <Translate contentKey="kmsApp.paraOther.help.paraValueSt" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraOtherEntity.paraValueSt}</dd>
            <dt>
              <span id="paraValueIn">
                <Translate contentKey="kmsApp.paraOther.paraValueIn">Para Value In</Translate>
              </span>
              <UncontrolledTooltip target="paraValueIn">
                <Translate contentKey="kmsApp.paraOther.help.paraValueIn" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraOtherEntity.paraValueIn} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="paraValueBo">
                <Translate contentKey="kmsApp.paraOther.paraValueBo">Para Value Bo</Translate>
              </span>
              <UncontrolledTooltip target="paraValueBo">
                <Translate contentKey="kmsApp.paraOther.help.paraValueBo" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraOtherEntity.paraValueBo ? 'true' : 'false'}</dd>
            <dt>
              <span id="paraValueJs">
                <Translate contentKey="kmsApp.paraOther.paraValueJs">Para Value Js</Translate>
              </span>
              <UncontrolledTooltip target="paraValueJs">
                <Translate contentKey="kmsApp.paraOther.help.paraValueJs" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraOtherEntity.paraValueJs}</dd>
            <dt>
              <span id="paraValueBl">
                <Translate contentKey="kmsApp.paraOther.paraValueBl">Para Value Bl</Translate>
              </span>
              <UncontrolledTooltip target="paraValueBl">
                <Translate contentKey="kmsApp.paraOther.help.paraValueBl" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              {paraOtherEntity.paraValueBl ? (
                <div>
                  <a onClick={openFile(paraOtherEntity.paraValueBlContentType, paraOtherEntity.paraValueBl)}>
                    <Translate contentKey="entity.action.open">Open</Translate>
                    &nbsp;
                  </a>
                  <span>
                    {paraOtherEntity.paraValueBlContentType}, {byteSize(paraOtherEntity.paraValueBl)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="imageBlob">
                <Translate contentKey="kmsApp.paraOther.imageBlob">Image Blob</Translate>
              </span>
              <UncontrolledTooltip target="imageBlob">
                <Translate contentKey="kmsApp.paraOther.help.imageBlob" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              {paraOtherEntity.imageBlob ? (
                <div>
                  <a onClick={openFile(paraOtherEntity.imageBlobContentType, paraOtherEntity.imageBlob)}>
                    <img
                      src={`data:${paraOtherEntity.imageBlobContentType};base64,${paraOtherEntity.imageBlob}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                  <span>
                    {paraOtherEntity.imageBlobContentType}, {byteSize(paraOtherEntity.imageBlob)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="imageBlobName">
                <Translate contentKey="kmsApp.paraOther.imageBlobName">Image Blob Name</Translate>
              </span>
              <UncontrolledTooltip target="imageBlobName">
                <Translate contentKey="kmsApp.paraOther.help.imageBlobName" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraOtherEntity.imageBlobName}</dd>
            <dt>
              <span id="usingFlag">
                <Translate contentKey="kmsApp.paraOther.usingFlag">Using Flag</Translate>
              </span>
              <UncontrolledTooltip target="usingFlag">
                <Translate contentKey="kmsApp.paraOther.help.usingFlag" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraOtherEntity.usingFlag ? 'true' : 'false'}</dd>
            <dt>
              <span id="remarks">
                <Translate contentKey="kmsApp.paraOther.remarks">Remarks</Translate>
              </span>
              <UncontrolledTooltip target="remarks">
                <Translate contentKey="kmsApp.paraOther.help.remarks" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraOtherEntity.remarks}</dd>
            <dt>
              <span id="validType">
                <Translate contentKey="kmsApp.paraOther.validType">Valid Type</Translate>
              </span>
              <UncontrolledTooltip target="validType">
                <Translate contentKey="kmsApp.paraOther.help.validType" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraOtherEntity.validType}</dd>
            <dt>
              <span id="validBegin">
                <Translate contentKey="kmsApp.paraOther.validBegin">Valid Begin</Translate>
              </span>
              <UncontrolledTooltip target="validBegin">
                <Translate contentKey="kmsApp.paraOther.help.validBegin" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraOtherEntity.validBegin} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="validEnd">
                <Translate contentKey="kmsApp.paraOther.validEnd">Valid End</Translate>
              </span>
              <UncontrolledTooltip target="validEnd">
                <Translate contentKey="kmsApp.paraOther.help.validEnd" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraOtherEntity.validEnd} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="createTime">
                <Translate contentKey="kmsApp.paraOther.createTime">Create Time</Translate>
              </span>
              <UncontrolledTooltip target="createTime">
                <Translate contentKey="kmsApp.paraOther.help.createTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraOtherEntity.createTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="modifyTime">
                <Translate contentKey="kmsApp.paraOther.modifyTime">Modify Time</Translate>
              </span>
              <UncontrolledTooltip target="modifyTime">
                <Translate contentKey="kmsApp.paraOther.help.modifyTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraOtherEntity.modifyTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="verifyTime">
                <Translate contentKey="kmsApp.paraOther.verifyTime">Verify Time</Translate>
              </span>
              <UncontrolledTooltip target="verifyTime">
                <Translate contentKey="kmsApp.paraOther.help.verifyTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraOtherEntity.verifyTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="kmsApp.paraOther.createdUser">Created User</Translate>
            </dt>
            <dd>{paraOtherEntity.createdUserName ? paraOtherEntity.createdUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraOther.modifiedUser">Modified User</Translate>
            </dt>
            <dd>{paraOtherEntity.modifiedUserName ? paraOtherEntity.modifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraOther.verifiedUser">Verified User</Translate>
            </dt>
            <dd>{paraOtherEntity.verifiedUserName ? paraOtherEntity.verifiedUserName : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/para-other-sdm-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/para-other-sdm-suffix/${paraOtherEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ paraOther }: IRootState) => ({
  paraOtherEntity: paraOther.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ParaOtherSdmSuffixDetail);
