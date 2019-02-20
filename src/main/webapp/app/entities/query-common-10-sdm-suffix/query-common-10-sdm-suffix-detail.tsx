import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './query-common-10-sdm-suffix.reducer';
import { IQueryCommon10SdmSuffix } from 'app/shared/model/query-common-10-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IQueryCommon10SdmSuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class QueryCommon10SdmSuffixDetail extends React.Component<IQueryCommon10SdmSuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { queryCommon10Entity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="kmsApp.queryCommon10.detail.title">QueryCommon10</Translate> [<b>{queryCommon10Entity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="queryName">
                <Translate contentKey="kmsApp.queryCommon10.queryName">Query Name</Translate>
              </span>
            </dt>
            <dd>{queryCommon10Entity.queryName}</dd>
            <dt>
              <span id="queryDate">
                <Translate contentKey="kmsApp.queryCommon10.queryDate">Query Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={queryCommon10Entity.queryDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="queryUser">
                <Translate contentKey="kmsApp.queryCommon10.queryUser">Query User</Translate>
              </span>
            </dt>
            <dd>{queryCommon10Entity.queryUser}</dd>
            <dt>
              <span id="col01">
                <Translate contentKey="kmsApp.queryCommon10.col01">Col 01</Translate>
              </span>
            </dt>
            <dd>{queryCommon10Entity.col01}</dd>
            <dt>
              <span id="col02">
                <Translate contentKey="kmsApp.queryCommon10.col02">Col 02</Translate>
              </span>
            </dt>
            <dd>{queryCommon10Entity.col02}</dd>
            <dt>
              <span id="col03">
                <Translate contentKey="kmsApp.queryCommon10.col03">Col 03</Translate>
              </span>
            </dt>
            <dd>{queryCommon10Entity.col03}</dd>
            <dt>
              <span id="col04">
                <Translate contentKey="kmsApp.queryCommon10.col04">Col 04</Translate>
              </span>
            </dt>
            <dd>{queryCommon10Entity.col04}</dd>
            <dt>
              <span id="col05">
                <Translate contentKey="kmsApp.queryCommon10.col05">Col 05</Translate>
              </span>
            </dt>
            <dd>{queryCommon10Entity.col05}</dd>
            <dt>
              <span id="col06">
                <Translate contentKey="kmsApp.queryCommon10.col06">Col 06</Translate>
              </span>
            </dt>
            <dd>{queryCommon10Entity.col06}</dd>
            <dt>
              <span id="col07">
                <Translate contentKey="kmsApp.queryCommon10.col07">Col 07</Translate>
              </span>
            </dt>
            <dd>{queryCommon10Entity.col07}</dd>
            <dt>
              <span id="col08">
                <Translate contentKey="kmsApp.queryCommon10.col08">Col 08</Translate>
              </span>
            </dt>
            <dd>{queryCommon10Entity.col08}</dd>
            <dt>
              <span id="col09">
                <Translate contentKey="kmsApp.queryCommon10.col09">Col 09</Translate>
              </span>
            </dt>
            <dd>{queryCommon10Entity.col09}</dd>
            <dt>
              <span id="col10">
                <Translate contentKey="kmsApp.queryCommon10.col10">Col 10</Translate>
              </span>
            </dt>
            <dd>{queryCommon10Entity.col10}</dd>
          </dl>
          <Button tag={Link} to="/entity/query-common-10-sdm-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/query-common-10-sdm-suffix/${queryCommon10Entity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ queryCommon10 }: IRootState) => ({
  queryCommon10Entity: queryCommon10.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(QueryCommon10SdmSuffixDetail);
