import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './query-common-50-sdm-suffix.reducer';
import { IQueryCommon50SdmSuffix } from 'app/shared/model/query-common-50-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IQueryCommon50SdmSuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class QueryCommon50SdmSuffixDetail extends React.Component<IQueryCommon50SdmSuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { queryCommon50Entity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="kmsApp.queryCommon50.detail.title">QueryCommon50</Translate> [<b>{queryCommon50Entity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="queryName">
                <Translate contentKey="kmsApp.queryCommon50.queryName">Query Name</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.queryName}</dd>
            <dt>
              <span id="queryDate">
                <Translate contentKey="kmsApp.queryCommon50.queryDate">Query Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={queryCommon50Entity.queryDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="queryUser">
                <Translate contentKey="kmsApp.queryCommon50.queryUser">Query User</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.queryUser}</dd>
            <dt>
              <span id="col01">
                <Translate contentKey="kmsApp.queryCommon50.col01">Col 01</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col01}</dd>
            <dt>
              <span id="col02">
                <Translate contentKey="kmsApp.queryCommon50.col02">Col 02</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col02}</dd>
            <dt>
              <span id="col03">
                <Translate contentKey="kmsApp.queryCommon50.col03">Col 03</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col03}</dd>
            <dt>
              <span id="col04">
                <Translate contentKey="kmsApp.queryCommon50.col04">Col 04</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col04}</dd>
            <dt>
              <span id="col05">
                <Translate contentKey="kmsApp.queryCommon50.col05">Col 05</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col05}</dd>
            <dt>
              <span id="col06">
                <Translate contentKey="kmsApp.queryCommon50.col06">Col 06</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col06}</dd>
            <dt>
              <span id="col07">
                <Translate contentKey="kmsApp.queryCommon50.col07">Col 07</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col07}</dd>
            <dt>
              <span id="col08">
                <Translate contentKey="kmsApp.queryCommon50.col08">Col 08</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col08}</dd>
            <dt>
              <span id="col09">
                <Translate contentKey="kmsApp.queryCommon50.col09">Col 09</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col09}</dd>
            <dt>
              <span id="col10">
                <Translate contentKey="kmsApp.queryCommon50.col10">Col 10</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col10}</dd>
            <dt>
              <span id="col11">
                <Translate contentKey="kmsApp.queryCommon50.col11">Col 11</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col11}</dd>
            <dt>
              <span id="col12">
                <Translate contentKey="kmsApp.queryCommon50.col12">Col 12</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col12}</dd>
            <dt>
              <span id="col13">
                <Translate contentKey="kmsApp.queryCommon50.col13">Col 13</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col13}</dd>
            <dt>
              <span id="col14">
                <Translate contentKey="kmsApp.queryCommon50.col14">Col 14</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col14}</dd>
            <dt>
              <span id="col15">
                <Translate contentKey="kmsApp.queryCommon50.col15">Col 15</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col15}</dd>
            <dt>
              <span id="col16">
                <Translate contentKey="kmsApp.queryCommon50.col16">Col 16</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col16}</dd>
            <dt>
              <span id="col17">
                <Translate contentKey="kmsApp.queryCommon50.col17">Col 17</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col17}</dd>
            <dt>
              <span id="col18">
                <Translate contentKey="kmsApp.queryCommon50.col18">Col 18</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col18}</dd>
            <dt>
              <span id="col19">
                <Translate contentKey="kmsApp.queryCommon50.col19">Col 19</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col19}</dd>
            <dt>
              <span id="col20">
                <Translate contentKey="kmsApp.queryCommon50.col20">Col 20</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col20}</dd>
            <dt>
              <span id="col21">
                <Translate contentKey="kmsApp.queryCommon50.col21">Col 21</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col21}</dd>
            <dt>
              <span id="col22">
                <Translate contentKey="kmsApp.queryCommon50.col22">Col 22</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col22}</dd>
            <dt>
              <span id="col23">
                <Translate contentKey="kmsApp.queryCommon50.col23">Col 23</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col23}</dd>
            <dt>
              <span id="col24">
                <Translate contentKey="kmsApp.queryCommon50.col24">Col 24</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col24}</dd>
            <dt>
              <span id="col25">
                <Translate contentKey="kmsApp.queryCommon50.col25">Col 25</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col25}</dd>
            <dt>
              <span id="col26">
                <Translate contentKey="kmsApp.queryCommon50.col26">Col 26</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col26}</dd>
            <dt>
              <span id="col27">
                <Translate contentKey="kmsApp.queryCommon50.col27">Col 27</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col27}</dd>
            <dt>
              <span id="col28">
                <Translate contentKey="kmsApp.queryCommon50.col28">Col 28</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col28}</dd>
            <dt>
              <span id="col29">
                <Translate contentKey="kmsApp.queryCommon50.col29">Col 29</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col29}</dd>
            <dt>
              <span id="col30">
                <Translate contentKey="kmsApp.queryCommon50.col30">Col 30</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col30}</dd>
            <dt>
              <span id="col31">
                <Translate contentKey="kmsApp.queryCommon50.col31">Col 31</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col31}</dd>
            <dt>
              <span id="col32">
                <Translate contentKey="kmsApp.queryCommon50.col32">Col 32</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col32}</dd>
            <dt>
              <span id="col33">
                <Translate contentKey="kmsApp.queryCommon50.col33">Col 33</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col33}</dd>
            <dt>
              <span id="col34">
                <Translate contentKey="kmsApp.queryCommon50.col34">Col 34</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col34}</dd>
            <dt>
              <span id="col35">
                <Translate contentKey="kmsApp.queryCommon50.col35">Col 35</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col35}</dd>
            <dt>
              <span id="col36">
                <Translate contentKey="kmsApp.queryCommon50.col36">Col 36</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col36}</dd>
            <dt>
              <span id="col37">
                <Translate contentKey="kmsApp.queryCommon50.col37">Col 37</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col37}</dd>
            <dt>
              <span id="col38">
                <Translate contentKey="kmsApp.queryCommon50.col38">Col 38</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col38}</dd>
            <dt>
              <span id="col39">
                <Translate contentKey="kmsApp.queryCommon50.col39">Col 39</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col39}</dd>
            <dt>
              <span id="col40">
                <Translate contentKey="kmsApp.queryCommon50.col40">Col 40</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col40}</dd>
            <dt>
              <span id="col41">
                <Translate contentKey="kmsApp.queryCommon50.col41">Col 41</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col41}</dd>
            <dt>
              <span id="col42">
                <Translate contentKey="kmsApp.queryCommon50.col42">Col 42</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col42}</dd>
            <dt>
              <span id="col43">
                <Translate contentKey="kmsApp.queryCommon50.col43">Col 43</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col43}</dd>
            <dt>
              <span id="col44">
                <Translate contentKey="kmsApp.queryCommon50.col44">Col 44</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col44}</dd>
            <dt>
              <span id="col45">
                <Translate contentKey="kmsApp.queryCommon50.col45">Col 45</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col45}</dd>
            <dt>
              <span id="col46">
                <Translate contentKey="kmsApp.queryCommon50.col46">Col 46</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col46}</dd>
            <dt>
              <span id="col47">
                <Translate contentKey="kmsApp.queryCommon50.col47">Col 47</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col47}</dd>
            <dt>
              <span id="col48">
                <Translate contentKey="kmsApp.queryCommon50.col48">Col 48</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col48}</dd>
            <dt>
              <span id="col49">
                <Translate contentKey="kmsApp.queryCommon50.col49">Col 49</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col49}</dd>
            <dt>
              <span id="col50">
                <Translate contentKey="kmsApp.queryCommon50.col50">Col 50</Translate>
              </span>
            </dt>
            <dd>{queryCommon50Entity.col50}</dd>
          </dl>
          <Button tag={Link} to="/entity/query-common-50-sdm-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/query-common-50-sdm-suffix/${queryCommon50Entity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ queryCommon50 }: IRootState) => ({
  queryCommon50Entity: queryCommon50.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(QueryCommon50SdmSuffixDetail);
