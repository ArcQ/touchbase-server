import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './base-member.reducer';
import { IBaseMember } from 'app/shared/model/base-member.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBaseMemberDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BaseMemberDetail = (props: IBaseMemberDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { baseMemberEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="touchbaseApp.baseMember.detail.title">BaseMember</Translate> [<b>{baseMemberEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="createdDate">
              <Translate contentKey="touchbaseApp.baseMember.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {baseMemberEntity.createdDate ? <TextFormat value={baseMemberEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="lastModifiedDate">
              <Translate contentKey="touchbaseApp.baseMember.lastModifiedDate">Last Modified Date</Translate>
            </span>
          </dt>
          <dd>
            {baseMemberEntity.lastModifiedDate ? (
              <TextFormat value={baseMemberEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="role">
              <Translate contentKey="touchbaseApp.baseMember.role">Role</Translate>
            </span>
          </dt>
          <dd>{baseMemberEntity.role}</dd>
          <dt>
            <Translate contentKey="touchbaseApp.baseMember.member">Member</Translate>
          </dt>
          <dd>{baseMemberEntity.memberId ? baseMemberEntity.memberId : ''}</dd>
          <dt>
            <Translate contentKey="touchbaseApp.baseMember.base">Base</Translate>
          </dt>
          <dd>{baseMemberEntity.baseId ? baseMemberEntity.baseId : ''}</dd>
        </dl>
        <Button tag={Link} to="/base-member" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/base-member/${baseMemberEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ baseMember }: IRootState) => ({
  baseMemberEntity: baseMember.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BaseMemberDetail);
