import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IQueryCommon50SdmSuffix, defaultValue } from 'app/shared/model/query-common-50-sdm-suffix.model';

export const ACTION_TYPES = {
  SEARCH_QUERYCOMMON50S: 'queryCommon50/SEARCH_QUERYCOMMON50S',
  FETCH_QUERYCOMMON50_LIST: 'queryCommon50/FETCH_QUERYCOMMON50_LIST',
  FETCH_QUERYCOMMON50: 'queryCommon50/FETCH_QUERYCOMMON50',
  CREATE_QUERYCOMMON50: 'queryCommon50/CREATE_QUERYCOMMON50',
  UPDATE_QUERYCOMMON50: 'queryCommon50/UPDATE_QUERYCOMMON50',
  DELETE_QUERYCOMMON50: 'queryCommon50/DELETE_QUERYCOMMON50',
  RESET: 'queryCommon50/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IQueryCommon50SdmSuffix>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type QueryCommon50SdmSuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: QueryCommon50SdmSuffixState = initialState, action): QueryCommon50SdmSuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_QUERYCOMMON50S):
    case REQUEST(ACTION_TYPES.FETCH_QUERYCOMMON50_LIST):
    case REQUEST(ACTION_TYPES.FETCH_QUERYCOMMON50):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_QUERYCOMMON50):
    case REQUEST(ACTION_TYPES.UPDATE_QUERYCOMMON50):
    case REQUEST(ACTION_TYPES.DELETE_QUERYCOMMON50):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_QUERYCOMMON50S):
    case FAILURE(ACTION_TYPES.FETCH_QUERYCOMMON50_LIST):
    case FAILURE(ACTION_TYPES.FETCH_QUERYCOMMON50):
    case FAILURE(ACTION_TYPES.CREATE_QUERYCOMMON50):
    case FAILURE(ACTION_TYPES.UPDATE_QUERYCOMMON50):
    case FAILURE(ACTION_TYPES.DELETE_QUERYCOMMON50):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_QUERYCOMMON50S):
    case SUCCESS(ACTION_TYPES.FETCH_QUERYCOMMON50_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_QUERYCOMMON50):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_QUERYCOMMON50):
    case SUCCESS(ACTION_TYPES.UPDATE_QUERYCOMMON50):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_QUERYCOMMON50):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/query-common-50-s';
const apiSearchUrl = 'api/_search/query-common-50-s';

// Actions

export const getSearchEntities: ICrudSearchAction<IQueryCommon50SdmSuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_QUERYCOMMON50S,
  payload: axios.get<IQueryCommon50SdmSuffix>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IQueryCommon50SdmSuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_QUERYCOMMON50_LIST,
    payload: axios.get<IQueryCommon50SdmSuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IQueryCommon50SdmSuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_QUERYCOMMON50,
    payload: axios.get<IQueryCommon50SdmSuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IQueryCommon50SdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_QUERYCOMMON50,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IQueryCommon50SdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_QUERYCOMMON50,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IQueryCommon50SdmSuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_QUERYCOMMON50,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
